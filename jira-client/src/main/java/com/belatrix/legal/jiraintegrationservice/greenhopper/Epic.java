/**
 * jira-client - a simple JIRA REST client
 * Copyright (c) 2013 Bob Carroll (bob.carroll@alum.rit.edu)
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.belatrix.legal.jiraintegrationservice.greenhopper;

import java.util.List;
import java.util.Map;

import com.belatrix.legal.jiraintegrationservice.jiraclient.Field;
import com.belatrix.legal.jiraintegrationservice.jiraclient.Issue;
import com.belatrix.legal.jiraintegrationservice.jiraclient.JiraException;
import com.belatrix.legal.jiraintegrationservice.jiraclient.RestClient;

import net.sf.json.JSONObject;

/**
 * Represents a GreenHopper epic issue.
 */
public class Epic extends GreenHopperIssue {

    public String epicLabel = null;
    public String epicColour = null;
    public EpicStats epicStats = null;

    /**
     * Creates an epic issue from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    protected Epic(RestClient restclient, JSONObject json) {
        super(restclient, json);

        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        epicLabel = Field.getString(map.get("epicLabel"));
        epicColour = Field.getString(map.get("epicColor"));
        epicStats = GreenHopperField.getEpicStats(map.get("epicStats"));
    }

    public String getEpicLabel() {
        return epicLabel;
    }

    public String getEpicColour() {
        return epicColour;
    }

    public EpicStats getEpicStats() {
        return epicStats;
    }
}

