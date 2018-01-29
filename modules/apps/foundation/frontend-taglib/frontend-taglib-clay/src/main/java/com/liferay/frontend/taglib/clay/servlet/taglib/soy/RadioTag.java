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

package com.liferay.frontend.taglib.clay.servlet.taglib.soy;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.base.BaseClayTag;

/**
 * @author Chema Balsas
 */
public class RadioTag extends BaseClayTag {

	public RadioTag() {
		super("radio", "ClayRadio");
	}

	public void setChecked(Boolean checked) {
		putValue("checked", checked);
	}

	public void setDisabled(Boolean disabled) {
		putValue("disabled", disabled);
	}

	public void setHideLabel(Boolean hideLabel) {
		putValue("hideLabel", hideLabel);
	}

	public void setInline(Boolean inline) {
		putValue("inline", inline);
	}

	public void setLabel(String label) {
		putValue("label", label);
	}

	public void setName(String name) {
		putValue("name", name);
	}

	public void setValue(String value) {
		putValue("value", value);
	}

}