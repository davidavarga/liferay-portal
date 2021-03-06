/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.exception.DuplicateFolderNameException;
import com.liferay.document.library.kernel.exception.InvalidFolderException;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.exception.SourceFileNameException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLTrashService;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.DuplicateLockException;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.trash.service.TrashEntryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Sergio González
 * @author Manuel de la Peña
 * @author Levente Hudák
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/edit_entry",
		"mvc.command.name=/document_library/move_entry"
	},
	service = MVCActionCommand.class
)
public class EditEntryMVCActionCommand extends BaseMVCActionCommand {

	protected void cancelCheckedOutEntries(ActionRequest actionRequest)
		throws Exception {

		long[] fileEntryIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsFileEntry");

		for (long fileEntryId : fileEntryIds) {
			_dlAppService.cancelCheckOut(fileEntryId);
		}
	}

	protected void checkInEntries(ActionRequest actionRequest)
		throws Exception {

		long[] fileEntryIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsFileEntry");

		DLVersionNumberIncrease dlVersionNumberIncrease =
			DLVersionNumberIncrease.valueOf(
				ParamUtil.getString(actionRequest, "versionIncrease"),
				DLVersionNumberIncrease.MINOR);
		String changeLog = ParamUtil.getString(actionRequest, "changeLog");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		for (long fileEntryId : fileEntryIds) {
			_dlAppService.checkInFileEntry(
				fileEntryId, dlVersionNumberIncrease, changeLog,
				serviceContext);
		}

		long[] fileShortcutIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsDLFileShortcut");

		for (long fileShortcutId : fileShortcutIds) {
			FileShortcut fileShortcut = _dlAppService.getFileShortcut(
				fileShortcutId);

			long toFileEntryId = fileShortcut.getToFileEntryId();

			if (!ArrayUtil.contains(fileEntryIds, toFileEntryId)) {
				_dlAppService.checkInFileEntry(
					toFileEntryId, dlVersionNumberIncrease, changeLog,
					serviceContext);
			}
		}
	}

	protected void checkOutEntries(ActionRequest actionRequest)
		throws Exception {

		long[] fileEntryIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsFileEntry");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		for (long fileEntryId : fileEntryIds) {
			_dlAppService.checkOutFileEntry(fileEntryId, serviceContext);
		}

		long[] fileShortcutIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsDLFileShortcut");

		for (long fileShortcutId : fileShortcutIds) {
			FileShortcut fileShortcut = _dlAppService.getFileShortcut(
				fileShortcutId);

			long toFileEntryId = fileShortcut.getToFileEntryId();

			if (!ArrayUtil.contains(fileEntryIds, toFileEntryId)) {
				_dlAppService.checkOutFileEntry(toFileEntryId, serviceContext);
			}
		}
	}

	protected void deleteEntries(
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		List<TrashedModel> trashedModels = new ArrayList<>();

		BulkSelection<Folder> folderBulkSelection =
			_folderBulkSelectionFactory.create(actionRequest.getParameterMap());

		folderBulkSelection.forEach(
			folder -> _deleteFolder(folder, moveToTrash, trashedModels));

		// Delete file shortcuts before file entries. See LPS-21348.

		BulkSelection<FileShortcut> fileShortcutBulkSelection =
			_fileShortcutBulkSelectionFactory.create(
				actionRequest.getParameterMap());

		fileShortcutBulkSelection.forEach(
			fileShortcut -> _deleteFileShortcut(
				fileShortcut, moveToTrash, trashedModels));

		BulkSelection<FileEntry> fileEntryBulkSelection =
			_fileEntryBulkSelectionFactory.create(
				actionRequest.getParameterMap());

		fileEntryBulkSelection.forEach(
			fileEntry -> _deleteFileEntry(
				fileEntry, moveToTrash, trashedModels));

		if (moveToTrash && !trashedModels.isEmpty()) {
			Map<String, Object> data = new HashMap<>();

			data.put("trashedModels", trashedModels);

			addDeleteSuccessData(actionRequest, data);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.CANCEL_CHECKOUT)) {
				cancelCheckedOutEntries(actionRequest);
			}
			else if (cmd.equals(Constants.CHECKIN)) {
				checkInEntries(actionRequest);
			}
			else if (cmd.equals(Constants.CHECKOUT)) {
				checkOutEntries(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteEntries(actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE)) {
				moveEntries(actionRequest);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteEntries(actionRequest, true);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				restoreTrashEntries(actionRequest);
			}

			WindowState windowState = actionRequest.getWindowState();

			if (windowState.equals(LiferayWindowState.POP_UP)) {
				String redirect = _portal.escapeRedirect(
					ParamUtil.getString(actionRequest, "redirect"));

				if (Validator.isNotNull(redirect)) {
					sendRedirect(actionRequest, actionResponse, redirect);
				}
			}
		}
		catch (DuplicateLockException | NoSuchFileEntryException |
			   NoSuchFolderException | PrincipalException e) {

			if (e instanceof DuplicateLockException) {
				DuplicateLockException dle = (DuplicateLockException)e;

				SessionErrors.add(actionRequest, dle.getClass(), dle.getLock());
			}
			else {
				SessionErrors.add(actionRequest, e.getClass());
			}

			actionResponse.setRenderParameter(
				"mvcPath", "/document_library/error.jsp");
		}
		catch (DuplicateFileEntryException | DuplicateFolderNameException |
			   SourceFileNameException e) {

			if (e instanceof DuplicateFileEntryException) {
				HttpServletResponse httpServletResponse =
					_portal.getHttpServletResponse(actionResponse);

				httpServletResponse.setStatus(
					ServletResponseConstants.SC_DUPLICATE_FILE_EXCEPTION);
			}

			SessionErrors.add(actionRequest, e.getClass(), e);
		}
		catch (AssetCategoryException | AssetTagException |
			   InvalidFolderException e) {

			SessionErrors.add(actionRequest, e.getClass(), e);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	protected void moveEntries(ActionRequest actionRequest) throws Exception {
		long newFolderId = ParamUtil.getLong(actionRequest, "newFolderId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntry.class.getName(), actionRequest);

		BulkSelection<Folder> folderBulkSelection =
			_folderBulkSelectionFactory.create(actionRequest.getParameterMap());

		folderBulkSelection.forEach(
			folder -> _dlAppService.moveFolder(
				folder.getFolderId(), newFolderId, serviceContext));

		BulkSelection<FileEntry> fileEntryBulkSelection =
			_fileEntryBulkSelectionFactory.create(
				actionRequest.getParameterMap());

		fileEntryBulkSelection.forEach(
			fileEntry -> _dlAppService.moveFileEntry(
				fileEntry.getFileEntryId(), newFolderId, serviceContext));

		BulkSelection<FileShortcut> fileShortcutBulkSelection =
			_fileShortcutBulkSelectionFactory.create(
				actionRequest.getParameterMap());

		fileShortcutBulkSelection.forEach(
			fileShortcut -> _dlAppService.updateFileShortcut(
				fileShortcut.getFileShortcutId(), newFolderId,
				fileShortcut.getToFileEntryId(), serviceContext));
	}

	protected void restoreTrashEntries(ActionRequest actionRequest)
		throws Exception {

		long[] restoreTrashEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreTrashEntryIds"), 0L);

		for (long restoreTrashEntryId : restoreTrashEntryIds) {
			_trashEntryService.restoreEntry(restoreTrashEntryId);
		}
	}

	private void _deleteFileEntry(
		FileEntry fileEntry, boolean moveToTrash,
		List<TrashedModel> trashedModels) {

		try {
			if (moveToTrash) {
				_dlTrashService.moveFileEntryToTrash(
					fileEntry.getFileEntryId());

				if (fileEntry.getModel() instanceof TrashedModel) {
					trashedModels.add((TrashedModel)fileEntry.getModel());
				}
			}
			else {
				_dlAppService.deleteFileEntry(fileEntry.getFileEntryId());
			}
		}
		catch (PortalException pe) {
			ReflectionUtil.throwException(pe);
		}
	}

	private void _deleteFileShortcut(
		FileShortcut fileShortcut, boolean moveToTrash,
		List<TrashedModel> trashedModels) {

		try {
			if (moveToTrash) {
				fileShortcut = _dlTrashService.moveFileShortcutToTrash(
					fileShortcut.getFileShortcutId());

				if (fileShortcut.getModel() instanceof TrashedModel) {
					trashedModels.add((TrashedModel)fileShortcut.getModel());
				}
			}
			else {
				_dlAppService.deleteFileShortcut(
					fileShortcut.getFileShortcutId());
			}
		}
		catch (PortalException pe) {
			ReflectionUtil.throwException(pe);
		}
	}

	private void _deleteFolder(
		Folder folder, boolean moveToTrash, List<TrashedModel> trashedModels) {

		try {
			if (moveToTrash) {
				if (folder.isMountPoint()) {
					return;
				}

				folder = _dlTrashService.moveFolderToTrash(
					folder.getFolderId());

				if (folder.getModel() instanceof TrashedModel) {
					trashedModels.add((TrashedModel)folder.getModel());
				}
			}
			else {
				_dlAppService.deleteFolder(folder.getFolderId());
			}
		}
		catch (PortalException pe) {
			ReflectionUtil.throwException(pe);
		}
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLTrashService _dlTrashService;

	@Reference(
		target = "(model.class.name=com.liferay.document.library.kernel.model.DLFileEntry)"
	)
	private BulkSelectionFactory<FileEntry> _fileEntryBulkSelectionFactory;

	@Reference(
		target = "(model.class.name=com.liferay.document.library.kernel.model.DLFileShortcut)"
	)
	private BulkSelectionFactory<FileShortcut>
		_fileShortcutBulkSelectionFactory;

	@Reference(
		target = "(model.class.name=com.liferay.document.library.kernel.model.DLFolder)"
	)
	private BulkSelectionFactory<Folder> _folderBulkSelectionFactory;

	@Reference
	private Portal _portal;

	@Reference
	private TrashEntryService _trashEntryService;

}