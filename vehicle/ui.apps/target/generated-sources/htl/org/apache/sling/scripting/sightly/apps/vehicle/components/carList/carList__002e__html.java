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
package org.apache.sling.scripting.sightly.apps.vehicle.components.carList;

import java.io.PrintWriter;
import java.util.Collection;
import javax.script.Bindings;

import org.apache.sling.scripting.sightly.render.RenderUnit;
import org.apache.sling.scripting.sightly.render.RenderContext;

public final class carList__002e__html extends RenderUnit {

    @Override
    protected final void render(PrintWriter out,
                                Bindings bindings,
                                Bindings arguments,
                                RenderContext renderContext) {
// Main Template Body -----------------------------------------------------------------------------

Object _global_placeholdertemplate = null;
Object _dynamic_hascontent = bindings.get("hascontent");
out.write("\r\n");
_global_placeholdertemplate = renderContext.call("use", "core/wcm/components/commons/v1/templates.html", obj());
out.write("<div class=\"cmp-carList\">\r\n\r\n    <div class=\"container\">\r\n\r\n        <span class=\"title text-center\">Cars</span>\r\n\r\n      <div class=\"row\">\r\n          <div class=\"col-3\">\r\n              <div>");
{
    Object var_resourcecontent0 = renderContext.call("includeResource", "carFilter", obj().with("resourceType", "vehicle/components/carFilter"));
    out.write(renderContext.getObjectModel().toString(var_resourcecontent0));
}
out.write("</div>\r\n          </div>\r\n\r\n          <div class=\"col-9\">\r\n              <div>");
{
    Object var_resourcecontent1 = renderContext.call("includeResource", "carItem", obj().with("resourceType", "vehicle/components/carItem"));
    out.write(renderContext.getObjectModel().toString(var_resourcecontent1));
}
out.write("</div>\r\n          </div>\r\n      </div>\r\n\r\n    </div>\r\n\r\n\r\n</div>\r\n");
{
    Object var_templatevar2 = renderContext.getObjectModel().resolveProperty(_global_placeholdertemplate, "placeholder");
    {
        boolean var_templateoptions3_field$_isempty = (!renderContext.getObjectModel().toBoolean(_dynamic_hascontent));
        {
            java.util.Map var_templateoptions3 = obj().with("isEmpty", var_templateoptions3_field$_isempty);
            callUnit(out, renderContext, var_templatevar2, var_templateoptions3);
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

