/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 ******************************************************************************/
package org.apache.sling.scripting.sightly.apps.vehicle.components.carFilter;

import java.io.PrintWriter;
import java.util.Collection;
import javax.script.Bindings;

import org.apache.sling.scripting.sightly.render.RenderUnit;
import org.apache.sling.scripting.sightly.render.RenderContext;

public final class carFilter__002e__html extends RenderUnit {

    @Override
    protected final void render(PrintWriter out,
                                Bindings bindings,
                                Bindings arguments,
                                RenderContext renderContext) {
// Main Template Body -----------------------------------------------------------------------------

Object _global_placeholdertemplate = null;
Object _dynamic_hascontent = bindings.get("hascontent");
_global_placeholdertemplate = renderContext.call("use", "core/wcm/components/commons/v1/templates.html", obj());
out.write("<div id=\"filters\">\r\n\r\n    <div class=\"border-0 p-5\">\r\n        <h1>Brands</h1>\r\n        <select class=\"form-select\" name=\"brand-select\" id=\"brand-select\">\r\n            <option>All</option>\r\n\r\n        </select>\r\n\r\n        <h1>Model</h1>\r\n        <select class=\"form-select\" name=\"model-select\" id=\"model-select\" disabled>\r\n            <option selected>All</option>\r\n        </select>\r\n\r\n        <h1>Year</h1>\r\n        <select class=\"form-select\" name=\"brand-select\" id=\"year-select\">\r\n            <option selected>All</option>\r\n        </select>\r\n    </div>\r\n\r\n</div>\r\n");
{
    Object var_templatevar0 = renderContext.getObjectModel().resolveProperty(_global_placeholdertemplate, "placeholder");
    {
        boolean var_templateoptions1_field$_isempty = (!renderContext.getObjectModel().toBoolean(_dynamic_hascontent));
        {
            java.util.Map var_templateoptions1 = obj().with("isEmpty", var_templateoptions1_field$_isempty);
            callUnit(out, renderContext, var_templatevar0, var_templateoptions1);
        }
    }
}


// End Of Main Template Body ----------------------------------------------------------------------
    }



    {
//Sub-Templates Initialization --------------------------------------------------------------------



//End of Sub-Templates Initialization -------------------------------------------------------------
    }

}

