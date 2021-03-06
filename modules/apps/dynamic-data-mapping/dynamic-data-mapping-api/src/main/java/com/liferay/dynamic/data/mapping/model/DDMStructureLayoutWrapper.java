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

package com.liferay.dynamic.data.mapping.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <p>
 * This class is a wrapper for {@link DDMStructureLayout}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLayout
 * @generated
 */
@ProviderType
public class DDMStructureLayoutWrapper
	extends BaseModelWrapper<DDMStructureLayout>
	implements DDMStructureLayout, ModelWrapper<DDMStructureLayout> {

	public DDMStructureLayoutWrapper(DDMStructureLayout ddmStructureLayout) {
		super(ddmStructureLayout);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("structureLayoutId", getStructureLayoutId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("structureVersionId", getStructureVersionId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("definition", getDefinition());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long structureLayoutId = (Long)attributes.get("structureLayoutId");

		if (structureLayoutId != null) {
			setStructureLayoutId(structureLayoutId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long structureVersionId = (Long)attributes.get("structureVersionId");

		if (structureVersionId != null) {
			setStructureVersionId(structureVersionId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String definition = (String)attributes.get("definition");

		if (definition != null) {
			setDefinition(definition);
		}
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the company ID of this ddm structure layout.
	 *
	 * @return the company ID of this ddm structure layout
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this ddm structure layout.
	 *
	 * @return the create date of this ddm structure layout
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public DDMFormLayout getDDMFormLayout() {
		return model.getDDMFormLayout();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the definition of this ddm structure layout.
	 *
	 * @return the definition of this ddm structure layout
	 */
	@Override
	public String getDefinition() {
		return model.getDefinition();
	}

	/**
	 * Returns the description of this ddm structure layout.
	 *
	 * @return the description of this ddm structure layout
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the localized description of this ddm structure layout in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized description of this ddm structure layout
	 */
	@Override
	public String getDescription(java.util.Locale locale) {
		return model.getDescription(locale);
	}

	/**
	 * Returns the localized description of this ddm structure layout in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this ddm structure layout. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getDescription(java.util.Locale locale, boolean useDefault) {
		return model.getDescription(locale, useDefault);
	}

	/**
	 * Returns the localized description of this ddm structure layout in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized description of this ddm structure layout
	 */
	@Override
	public String getDescription(String languageId) {
		return model.getDescription(languageId);
	}

	/**
	 * Returns the localized description of this ddm structure layout in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this ddm structure layout
	 */
	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return model.getDescription(languageId, useDefault);
	}

	@Override
	public String getDescriptionCurrentLanguageId() {
		return model.getDescriptionCurrentLanguageId();
	}

	@Override
	public String getDescriptionCurrentValue() {
		return model.getDescriptionCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized descriptions of this ddm structure layout.
	 *
	 * @return the locales and localized descriptions of this ddm structure layout
	 */
	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return model.getDescriptionMap();
	}

	/**
	 * Returns the group ID of this ddm structure layout.
	 *
	 * @return the group ID of this ddm structure layout
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this ddm structure layout.
	 *
	 * @return the modified date of this ddm structure layout
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this ddm structure layout.
	 *
	 * @return the name of this ddm structure layout
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the localized name of this ddm structure layout in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this ddm structure layout
	 */
	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	/**
	 * Returns the localized name of this ddm structure layout in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this ddm structure layout. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return model.getName(locale, useDefault);
	}

	/**
	 * Returns the localized name of this ddm structure layout in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this ddm structure layout
	 */
	@Override
	public String getName(String languageId) {
		return model.getName(languageId);
	}

	/**
	 * Returns the localized name of this ddm structure layout in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this ddm structure layout
	 */
	@Override
	public String getName(String languageId, boolean useDefault) {
		return model.getName(languageId, useDefault);
	}

	@Override
	public String getNameCurrentLanguageId() {
		return model.getNameCurrentLanguageId();
	}

	@Override
	public String getNameCurrentValue() {
		return model.getNameCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized names of this ddm structure layout.
	 *
	 * @return the locales and localized names of this ddm structure layout
	 */
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return model.getNameMap();
	}

	/**
	 * Returns the primary key of this ddm structure layout.
	 *
	 * @return the primary key of this ddm structure layout
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the structure layout ID of this ddm structure layout.
	 *
	 * @return the structure layout ID of this ddm structure layout
	 */
	@Override
	public long getStructureLayoutId() {
		return model.getStructureLayoutId();
	}

	/**
	 * Returns the structure version ID of this ddm structure layout.
	 *
	 * @return the structure version ID of this ddm structure layout
	 */
	@Override
	public long getStructureVersionId() {
		return model.getStructureVersionId();
	}

	/**
	 * Returns the user ID of this ddm structure layout.
	 *
	 * @return the user ID of this ddm structure layout
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this ddm structure layout.
	 *
	 * @return the user name of this ddm structure layout
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this ddm structure layout.
	 *
	 * @return the user uuid of this ddm structure layout
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this ddm structure layout.
	 *
	 * @return the uuid of this ddm structure layout
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {

		model.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
			java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {

		model.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	 * Sets the company ID of this ddm structure layout.
	 *
	 * @param companyId the company ID of this ddm structure layout
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this ddm structure layout.
	 *
	 * @param createDate the create date of this ddm structure layout
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the definition of this ddm structure layout.
	 *
	 * @param definition the definition of this ddm structure layout
	 */
	@Override
	public void setDefinition(String definition) {
		model.setDefinition(definition);
	}

	/**
	 * Sets the description of this ddm structure layout.
	 *
	 * @param description the description of this ddm structure layout
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the localized description of this ddm structure layout in the language.
	 *
	 * @param description the localized description of this ddm structure layout
	 * @param locale the locale of the language
	 */
	@Override
	public void setDescription(String description, java.util.Locale locale) {
		model.setDescription(description, locale);
	}

	/**
	 * Sets the localized description of this ddm structure layout in the language, and sets the default locale.
	 *
	 * @param description the localized description of this ddm structure layout
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDescription(
		String description, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(String languageId) {
		model.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized descriptions of this ddm structure layout from the map of locales and localized descriptions.
	 *
	 * @param descriptionMap the locales and localized descriptions of this ddm structure layout
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap) {

		model.setDescriptionMap(descriptionMap);
	}

	/**
	 * Sets the localized descriptions of this ddm structure layout from the map of locales and localized descriptions, and sets the default locale.
	 *
	 * @param descriptionMap the locales and localized descriptions of this ddm structure layout
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap,
		java.util.Locale defaultLocale) {

		model.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	 * Sets the group ID of this ddm structure layout.
	 *
	 * @param groupId the group ID of this ddm structure layout
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this ddm structure layout.
	 *
	 * @param modifiedDate the modified date of this ddm structure layout
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this ddm structure layout.
	 *
	 * @param name the name of this ddm structure layout
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the localized name of this ddm structure layout in the language.
	 *
	 * @param name the localized name of this ddm structure layout
	 * @param locale the locale of the language
	 */
	@Override
	public void setName(String name, java.util.Locale locale) {
		model.setName(name, locale);
	}

	/**
	 * Sets the localized name of this ddm structure layout in the language, and sets the default locale.
	 *
	 * @param name the localized name of this ddm structure layout
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setName(
		String name, java.util.Locale locale, java.util.Locale defaultLocale) {

		model.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		model.setNameCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized names of this ddm structure layout from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this ddm structure layout
	 */
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		model.setNameMap(nameMap);
	}

	/**
	 * Sets the localized names of this ddm structure layout from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this ddm structure layout
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setNameMap(
		Map<java.util.Locale, String> nameMap, java.util.Locale defaultLocale) {

		model.setNameMap(nameMap, defaultLocale);
	}

	/**
	 * Sets the primary key of this ddm structure layout.
	 *
	 * @param primaryKey the primary key of this ddm structure layout
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the structure layout ID of this ddm structure layout.
	 *
	 * @param structureLayoutId the structure layout ID of this ddm structure layout
	 */
	@Override
	public void setStructureLayoutId(long structureLayoutId) {
		model.setStructureLayoutId(structureLayoutId);
	}

	/**
	 * Sets the structure version ID of this ddm structure layout.
	 *
	 * @param structureVersionId the structure version ID of this ddm structure layout
	 */
	@Override
	public void setStructureVersionId(long structureVersionId) {
		model.setStructureVersionId(structureVersionId);
	}

	/**
	 * Sets the user ID of this ddm structure layout.
	 *
	 * @param userId the user ID of this ddm structure layout
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this ddm structure layout.
	 *
	 * @param userName the user name of this ddm structure layout
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this ddm structure layout.
	 *
	 * @param userUuid the user uuid of this ddm structure layout
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this ddm structure layout.
	 *
	 * @param uuid the uuid of this ddm structure layout
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected DDMStructureLayoutWrapper wrap(
		DDMStructureLayout ddmStructureLayout) {

		return new DDMStructureLayoutWrapper(ddmStructureLayout);
	}

}