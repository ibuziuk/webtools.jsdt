package org.eclipse.wst.jsdt.js.grunt.internal.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.wst.jsdt.core.dom.ASTVisitor;
import org.eclipse.wst.jsdt.core.dom.Expression;
import org.eclipse.wst.jsdt.core.dom.FunctionInvocation;
import org.eclipse.wst.jsdt.core.dom.SimpleName;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author "Ilya Buziuk (ibuziuk)"
 */
public class GruntVisitor extends ASTVisitor {
	private List<String> tasks;
	private static final String GRUNT = "grunt"; //$NON-NLS-1$
	private static final String REGISTER_TASK = "registerTask"; //$NON-NLS-1$
	private static final String INIT_CONFIG = "initConfig"; //$NON-NLS-1$
	
	public GruntVisitor() {
		super();
		this.tasks = new ArrayList<String>();
	}
	
	@SuppressWarnings("unchecked")
	public boolean visit(FunctionInvocation node) {
		SimpleName functionName = node.getName();
		Expression expression = node.getExpression();
		List<Expression> arguments = node.arguments();
		
		if (functionName != null && expression != null && arguments != null) {
		// Test for grunt
		if (REGISTER_TASK.equals(functionName.toString()) && GRUNT.equals(expression.toString())) { 
			if (arguments.size() == 2 ) {
				// TODO: fix the quotes issue in a better way
				tasks.add(arguments.get(0).toString().replaceAll("'", "").replaceAll("\"", ""));  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			}
		} else if (INIT_CONFIG.equals(functionName.toString())) {
			// This does not work in many cases
			for (Expression a : arguments) {
				JsonParser parser = new JsonParser();
				JsonElement element = parser.parse(a.toString());
				System.out.println(a.properties());
				JsonObject asJsonObject = element.getAsJsonObject();
				Set<Entry<String, JsonElement>> entrySet = asJsonObject.entrySet();
				for (Entry<String, JsonElement> entry: entrySet) {
					tasks.add(entry.getKey());
				}
			}
		} 
		}
		
		return true;
	}
	
	public List<String> getTasks() {
		return this.tasks;
	}
}
