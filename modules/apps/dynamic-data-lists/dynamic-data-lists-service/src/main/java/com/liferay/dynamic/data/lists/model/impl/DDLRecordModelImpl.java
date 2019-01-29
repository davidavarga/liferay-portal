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

package com.liferay.dynamic.data.lists.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordModel;
import com.liferay.dynamic.data.lists.model.DDLRecordSoap;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the DDLRecord service. Represents a row in the &quot;DDLRecord&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link DDLRecordModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link DDLRecordImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordImpl
 * @see DDLRecord
 * @see DDLRecordModel
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class DDLRecordModelImpl extends BaseModelImpl<DDLRecord>
	implements DDLRecordModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a ddl record model instance should use the {@link DDLRecord} interface instead.
	 */
	public static final String TABLE_NAME = "DDLRecord";
	public static final Object[][] TABLE_COLUMNS = {
			{ "mvccVersion", Types.BIGINT },
			{ "uuid_", Types.VARCHAR },
			{ "recordId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "versionUserId", Types.BIGINT },
			{ "versionUserName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "DDMStorageId", Types.BIGINT },
			{ "recordSetId", Types.BIGINT },
			{ "recordSetVersion", Types.VARCHAR },
			{ "version", Types.VARCHAR },
			{ "displayIndex", Types.INTEGER },
			{ "lastPublishDate", Types.TIMESTAMP }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("recordId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("versionUserId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("versionUserName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("DDMStorageId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("recordSetId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("recordSetVersion", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("version", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("displayIndex", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE = "create table DDLRecord (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,recordId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,versionUserId LONG,versionUserName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,DDMStorageId LONG,recordSetId LONG,recordSetVersion VARCHAR(75) null,version VARCHAR(75) null,displayIndex INTEGER,lastPublishDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table DDLRecord";
	public static final String ORDER_BY_JPQL = " ORDER BY ddlRecord.recordId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY DDLRecord.recordId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.dynamic.data.lists.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.dynamic.data.lists.model.DDLRecord"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.dynamic.data.lists.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.dynamic.data.lists.model.DDLRecord"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.dynamic.data.lists.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.dynamic.data.lists.model.DDLRecord"),
			true);
	public static final long COMPANYID_COLUMN_BITMASK = 1L;
	public static final long GROUPID_COLUMN_BITMASK = 2L;
	public static final long RECORDSETID_COLUMN_BITMASK = 4L;
	public static final long RECORDSETVERSION_COLUMN_BITMASK = 8L;
	public static final long USERID_COLUMN_BITMASK = 16L;
	public static final long UUID_COLUMN_BITMASK = 32L;
	public static final long RECORDID_COLUMN_BITMASK = 64L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static DDLRecord toModel(DDLRecordSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		DDLRecord model = new DDLRecordImpl();

		model.setMvccVersion(soapModel.getMvccVersion());
		model.setUuid(soapModel.getUuid());
		model.setRecordId(soapModel.getRecordId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setVersionUserId(soapModel.getVersionUserId());
		model.setVersionUserName(soapModel.getVersionUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setDDMStorageId(soapModel.getDDMStorageId());
		model.setRecordSetId(soapModel.getRecordSetId());
		model.setRecordSetVersion(soapModel.getRecordSetVersion());
		model.setVersion(soapModel.getVersion());
		model.setDisplayIndex(soapModel.getDisplayIndex());
		model.setLastPublishDate(soapModel.getLastPublishDate());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<DDLRecord> toModels(DDLRecordSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<DDLRecord> models = new ArrayList<DDLRecord>(soapModels.length);

		for (DDLRecordSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.dynamic.data.lists.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.dynamic.data.lists.model.DDLRecord"));

	public DDLRecordModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _recordId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setRecordId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _recordId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return DDLRecord.class;
	}

	@Override
	public String getModelClassName() {
		return DDLRecord.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<DDLRecord, Object>> attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<DDLRecord, Object>> entry : attributeGetterFunctions.entrySet()) {
			String attributeName = entry.getKey();
			Function<DDLRecord, Object> attributeGetterFunction = entry.getValue();

			attributes.put(attributeName,
				attributeGetterFunction.apply((DDLRecord)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<DDLRecord, Object>> attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<DDLRecord, Object> attributeSetterBiConsumer = attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept((DDLRecord)this,
					entry.getValue());
			}
		}
	}

	public Map<String, Function<DDLRecord, Object>> getAttributeGetterFunctions() {
		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<DDLRecord, Object>> getAttributeSetterBiConsumers() {
		return _attributeSetterBiConsumers;
	}

	private static final Map<String, Function<DDLRecord, Object>> _attributeGetterFunctions;
	private static final Map<String, BiConsumer<DDLRecord, Object>> _attributeSetterBiConsumers;

	static {
		Map<String, Function<DDLRecord, Object>> attributeGetterFunctions = new LinkedHashMap<String, Function<DDLRecord, Object>>();
		Map<String, BiConsumer<DDLRecord, ?>> attributeSetterBiConsumers = new LinkedHashMap<String, BiConsumer<DDLRecord, ?>>();


		attributeGetterFunctions.put("mvccVersion", DDLRecord::getMvccVersion);
		attributeSetterBiConsumers.put("mvccVersion", (BiConsumer<DDLRecord, Long>)DDLRecord::setMvccVersion);
		attributeGetterFunctions.put("uuid", DDLRecord::getUuid);
		attributeSetterBiConsumers.put("uuid", (BiConsumer<DDLRecord, String>)DDLRecord::setUuid);
		attributeGetterFunctions.put("recordId", DDLRecord::getRecordId);
		attributeSetterBiConsumers.put("recordId", (BiConsumer<DDLRecord, Long>)DDLRecord::setRecordId);
		attributeGetterFunctions.put("groupId", DDLRecord::getGroupId);
		attributeSetterBiConsumers.put("groupId", (BiConsumer<DDLRecord, Long>)DDLRecord::setGroupId);
		attributeGetterFunctions.put("companyId", DDLRecord::getCompanyId);
		attributeSetterBiConsumers.put("companyId", (BiConsumer<DDLRecord, Long>)DDLRecord::setCompanyId);
		attributeGetterFunctions.put("userId", DDLRecord::getUserId);
		attributeSetterBiConsumers.put("userId", (BiConsumer<DDLRecord, Long>)DDLRecord::setUserId);
		attributeGetterFunctions.put("userName", DDLRecord::getUserName);
		attributeSetterBiConsumers.put("userName", (BiConsumer<DDLRecord, String>)DDLRecord::setUserName);
		attributeGetterFunctions.put("versionUserId", DDLRecord::getVersionUserId);
		attributeSetterBiConsumers.put("versionUserId", (BiConsumer<DDLRecord, Long>)DDLRecord::setVersionUserId);
		attributeGetterFunctions.put("versionUserName", DDLRecord::getVersionUserName);
		attributeSetterBiConsumers.put("versionUserName", (BiConsumer<DDLRecord, String>)DDLRecord::setVersionUserName);
		attributeGetterFunctions.put("createDate", DDLRecord::getCreateDate);
		attributeSetterBiConsumers.put("createDate", (BiConsumer<DDLRecord, Date>)DDLRecord::setCreateDate);
		attributeGetterFunctions.put("modifiedDate", DDLRecord::getModifiedDate);
		attributeSetterBiConsumers.put("modifiedDate", (BiConsumer<DDLRecord, Date>)DDLRecord::setModifiedDate);
		attributeGetterFunctions.put("DDMStorageId", DDLRecord::getDDMStorageId);
		attributeSetterBiConsumers.put("DDMStorageId", (BiConsumer<DDLRecord, Long>)DDLRecord::setDDMStorageId);
		attributeGetterFunctions.put("recordSetId", DDLRecord::getRecordSetId);
		attributeSetterBiConsumers.put("recordSetId", (BiConsumer<DDLRecord, Long>)DDLRecord::setRecordSetId);
		attributeGetterFunctions.put("recordSetVersion", DDLRecord::getRecordSetVersion);
		attributeSetterBiConsumers.put("recordSetVersion", (BiConsumer<DDLRecord, String>)DDLRecord::setRecordSetVersion);
		attributeGetterFunctions.put("version", DDLRecord::getVersion);
		attributeSetterBiConsumers.put("version", (BiConsumer<DDLRecord, String>)DDLRecord::setVersion);
		attributeGetterFunctions.put("displayIndex", DDLRecord::getDisplayIndex);
		attributeSetterBiConsumers.put("displayIndex", (BiConsumer<DDLRecord, Integer>)DDLRecord::setDisplayIndex);
		attributeGetterFunctions.put("lastPublishDate", DDLRecord::getLastPublishDate);
		attributeSetterBiConsumers.put("lastPublishDate", (BiConsumer<DDLRecord, Date>)DDLRecord::setLastPublishDate);


		_attributeGetterFunctions = Collections.unmodifiableMap(attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap((Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	@JSON
	@Override
	public String getUuid() {
		if (_uuid == null) {
			return "";
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		_columnBitmask |= UUID_COLUMN_BITMASK;

		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@JSON
	@Override
	public long getRecordId() {
		return _recordId;
	}

	@Override
	public void setRecordId(long recordId) {
		_recordId = recordId;
	}

	@JSON
	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_columnBitmask |= GROUPID_COLUMN_BITMASK;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_columnBitmask |= USERID_COLUMN_BITMASK;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	@JSON
	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@JSON
	@Override
	public long getVersionUserId() {
		return _versionUserId;
	}

	@Override
	public void setVersionUserId(long versionUserId) {
		_versionUserId = versionUserId;
	}

	@Override
	public String getVersionUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getVersionUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setVersionUserUuid(String versionUserUuid) {
	}

	@JSON
	@Override
	public String getVersionUserName() {
		if (_versionUserName == null) {
			return "";
		}
		else {
			return _versionUserName;
		}
	}

	@Override
	public void setVersionUserName(String versionUserName) {
		_versionUserName = versionUserName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@JSON
	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@JSON
	@Override
	public long getDDMStorageId() {
		return _DDMStorageId;
	}

	@Override
	public void setDDMStorageId(long DDMStorageId) {
		_DDMStorageId = DDMStorageId;
	}

	@JSON
	@Override
	public long getRecordSetId() {
		return _recordSetId;
	}

	@Override
	public void setRecordSetId(long recordSetId) {
		_columnBitmask |= RECORDSETID_COLUMN_BITMASK;

		if (!_setOriginalRecordSetId) {
			_setOriginalRecordSetId = true;

			_originalRecordSetId = _recordSetId;
		}

		_recordSetId = recordSetId;
	}

	public long getOriginalRecordSetId() {
		return _originalRecordSetId;
	}

	@JSON
	@Override
	public String getRecordSetVersion() {
		if (_recordSetVersion == null) {
			return "";
		}
		else {
			return _recordSetVersion;
		}
	}

	@Override
	public void setRecordSetVersion(String recordSetVersion) {
		_columnBitmask |= RECORDSETVERSION_COLUMN_BITMASK;

		if (_originalRecordSetVersion == null) {
			_originalRecordSetVersion = _recordSetVersion;
		}

		_recordSetVersion = recordSetVersion;
	}

	public String getOriginalRecordSetVersion() {
		return GetterUtil.getString(_originalRecordSetVersion);
	}

	@JSON
	@Override
	public String getVersion() {
		if (_version == null) {
			return "";
		}
		else {
			return _version;
		}
	}

	@Override
	public void setVersion(String version) {
		_version = version;
	}

	@JSON
	@Override
	public int getDisplayIndex() {
		return _displayIndex;
	}

	@Override
	public void setDisplayIndex(int displayIndex) {
		_displayIndex = displayIndex;
	}

	@JSON
	@Override
	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(PortalUtil.getClassNameId(
				DDLRecord.class.getName()));
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			DDLRecord.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public DDLRecord toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (DDLRecord)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		DDLRecordImpl ddlRecordImpl = new DDLRecordImpl();

		ddlRecordImpl.setMvccVersion(getMvccVersion());
		ddlRecordImpl.setUuid(getUuid());
		ddlRecordImpl.setRecordId(getRecordId());
		ddlRecordImpl.setGroupId(getGroupId());
		ddlRecordImpl.setCompanyId(getCompanyId());
		ddlRecordImpl.setUserId(getUserId());
		ddlRecordImpl.setUserName(getUserName());
		ddlRecordImpl.setVersionUserId(getVersionUserId());
		ddlRecordImpl.setVersionUserName(getVersionUserName());
		ddlRecordImpl.setCreateDate(getCreateDate());
		ddlRecordImpl.setModifiedDate(getModifiedDate());
		ddlRecordImpl.setDDMStorageId(getDDMStorageId());
		ddlRecordImpl.setRecordSetId(getRecordSetId());
		ddlRecordImpl.setRecordSetVersion(getRecordSetVersion());
		ddlRecordImpl.setVersion(getVersion());
		ddlRecordImpl.setDisplayIndex(getDisplayIndex());
		ddlRecordImpl.setLastPublishDate(getLastPublishDate());

		ddlRecordImpl.resetOriginalValues();

		return ddlRecordImpl;
	}

	@Override
	public int compareTo(DDLRecord ddlRecord) {
		long primaryKey = ddlRecord.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDLRecord)) {
			return false;
		}

		DDLRecord ddlRecord = (DDLRecord)obj;

		long primaryKey = ddlRecord.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		DDLRecordModelImpl ddlRecordModelImpl = this;

		ddlRecordModelImpl._originalUuid = ddlRecordModelImpl._uuid;

		ddlRecordModelImpl._originalGroupId = ddlRecordModelImpl._groupId;

		ddlRecordModelImpl._setOriginalGroupId = false;

		ddlRecordModelImpl._originalCompanyId = ddlRecordModelImpl._companyId;

		ddlRecordModelImpl._setOriginalCompanyId = false;

		ddlRecordModelImpl._originalUserId = ddlRecordModelImpl._userId;

		ddlRecordModelImpl._setOriginalUserId = false;

		ddlRecordModelImpl._setModifiedDate = false;

		ddlRecordModelImpl._originalRecordSetId = ddlRecordModelImpl._recordSetId;

		ddlRecordModelImpl._setOriginalRecordSetId = false;

		ddlRecordModelImpl._originalRecordSetVersion = ddlRecordModelImpl._recordSetVersion;

		ddlRecordModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<DDLRecord> toCacheModel() {
		DDLRecordCacheModel ddlRecordCacheModel = new DDLRecordCacheModel();

		ddlRecordCacheModel.mvccVersion = getMvccVersion();

		ddlRecordCacheModel.uuid = getUuid();

		String uuid = ddlRecordCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			ddlRecordCacheModel.uuid = null;
		}

		ddlRecordCacheModel.recordId = getRecordId();

		ddlRecordCacheModel.groupId = getGroupId();

		ddlRecordCacheModel.companyId = getCompanyId();

		ddlRecordCacheModel.userId = getUserId();

		ddlRecordCacheModel.userName = getUserName();

		String userName = ddlRecordCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			ddlRecordCacheModel.userName = null;
		}

		ddlRecordCacheModel.versionUserId = getVersionUserId();

		ddlRecordCacheModel.versionUserName = getVersionUserName();

		String versionUserName = ddlRecordCacheModel.versionUserName;

		if ((versionUserName != null) && (versionUserName.length() == 0)) {
			ddlRecordCacheModel.versionUserName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			ddlRecordCacheModel.createDate = createDate.getTime();
		}
		else {
			ddlRecordCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			ddlRecordCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			ddlRecordCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		ddlRecordCacheModel.DDMStorageId = getDDMStorageId();

		ddlRecordCacheModel.recordSetId = getRecordSetId();

		ddlRecordCacheModel.recordSetVersion = getRecordSetVersion();

		String recordSetVersion = ddlRecordCacheModel.recordSetVersion;

		if ((recordSetVersion != null) && (recordSetVersion.length() == 0)) {
			ddlRecordCacheModel.recordSetVersion = null;
		}

		ddlRecordCacheModel.version = getVersion();

		String version = ddlRecordCacheModel.version;

		if ((version != null) && (version.length() == 0)) {
			ddlRecordCacheModel.version = null;
		}

		ddlRecordCacheModel.displayIndex = getDisplayIndex();

		Date lastPublishDate = getLastPublishDate();

		if (lastPublishDate != null) {
			ddlRecordCacheModel.lastPublishDate = lastPublishDate.getTime();
		}
		else {
			ddlRecordCacheModel.lastPublishDate = Long.MIN_VALUE;
		}

		return ddlRecordCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<DDLRecord, Object>> attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler((4 * attributeGetterFunctions.size()) +
				2);

		sb.append("{");

		for (Map.Entry<String, Function<DDLRecord, Object>> entry : attributeGetterFunctions.entrySet()) {
			String attributeName = entry.getKey();
			Function<DDLRecord, Object> attributeGetterFunction = entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((DDLRecord)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<DDLRecord, Object>> attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler((5 * attributeGetterFunctions.size()) +
				4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<DDLRecord, Object>> entry : attributeGetterFunctions.entrySet()) {
			String attributeName = entry.getKey();
			Function<DDLRecord, Object> attributeGetterFunction = entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((DDLRecord)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = DDLRecord.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			DDLRecord.class, ModelWrapper.class
		};
	private long _mvccVersion;
	private String _uuid;
	private String _originalUuid;
	private long _recordId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private String _userName;
	private long _versionUserId;
	private String _versionUserName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _DDMStorageId;
	private long _recordSetId;
	private long _originalRecordSetId;
	private boolean _setOriginalRecordSetId;
	private String _recordSetVersion;
	private String _originalRecordSetVersion;
	private String _version;
	private int _displayIndex;
	private Date _lastPublishDate;
	private long _columnBitmask;
	private DDLRecord _escapedModel;
}