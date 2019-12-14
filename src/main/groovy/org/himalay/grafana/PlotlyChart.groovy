package org.himalay.grafana

import groovy.json.JsonSlurper
import io.micronaut.context.annotation.Value
import org.mozilla.javascript.ScriptableObject;

import javax.script.Bindings
import javax.script.ScriptContext
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.SimpleBindings

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import groovy.json.JsonBuilder
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

/**
 * This class represents a Plotly mock.
 */
public class PlotlyChart {
	Map plotDefinition = null;

	public PlotlyChart(String mockName, File mocksFolder){
		File mockFile = new File(mocksFolder, mockName);
		read(mockFile);
	}

	public PlotlyChart(File mockFile){
		read(mockFile);
	}

	private void read(File mockFile){
		plotDefinition = new JsonSlurper().parse(mockFile);
	}
	public String chartDefAsJSON() {
		new JsonBuilder(plotDefinition).toPrettyString();
	}
}
