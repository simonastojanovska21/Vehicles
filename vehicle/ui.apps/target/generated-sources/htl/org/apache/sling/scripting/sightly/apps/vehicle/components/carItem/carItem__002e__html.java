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
package org.apache.sling.scripting.sightly.apps.vehicle.components.carItem;

import java.io.PrintWriter;
import java.util.Collection;
import javax.script.Bindings;

import org.apache.sling.scripting.sightly.render.RenderUnit;
import org.apache.sling.scripting.sightly.render.RenderContext;

public final class carItem__002e__html extends RenderUnit {

    @Override
    protected final void render(PrintWriter out,
                                Bindings bindings,
                                Bindings arguments,
                                RenderContext renderContext) {
// Main Template Body -----------------------------------------------------------------------------

Object _global_car = null;
Object _global_placeholdertemplate = null;
Object _dynamic_hascontent = bindings.get("hascontent");
_global_car = renderContext.call("use", com.vehicle.core.models.Car.class.getName(), obj());
_global_placeholdertemplate = renderContext.call("use", "core/wcm/components/commons/v1/templates.html", obj());
out.write("<div>\r\n\r\n    <div class=\"border-0 p-5\">\r\n        <img");
{
    Object var_attrvalue0 = renderContext.getObjectModel().resolveProperty(_global_car, "imageUrl");
    {
        Object var_attrcontent1 = renderContext.call("xss", var_attrvalue0, "uri");
        {
            boolean var_shoulddisplayattr3 = (((null != var_attrcontent1) && (!"".equals(var_attrcontent1))) && ((!"".equals(var_attrvalue0)) && (!((Object)false).equals(var_attrvalue0))));
            if (var_shoulddisplayattr3) {
                out.write(" src");
                {
                    boolean var_istrueattr2 = (var_attrvalue0.equals(true));
                    if (!var_istrueattr2) {
                        out.write("=\"");
                        out.write(renderContext.getObjectModel().toString(var_attrcontent1));
                        out.write("\"");
                    }
                }
            }
        }
    }
}
out.write(" class=\"card-img-top itemImages\"/>\r\n        <div className=\"card-body\">\r\n            <h2 class=\"cmp-item__title\">");
{
    String var_4 = (((("\r\n                " + renderContext.getObjectModel().toString(renderContext.call("xss", renderContext.getObjectModel().resolveProperty(_global_car, "brand"), "text"))) + " - ") + renderContext.getObjectModel().toString(renderContext.call("xss", renderContext.getObjectModel().resolveProperty(_global_car, "model"), "text"))) + "\r\n            ");
    out.write(renderContext.getObjectModel().toString(var_4));
}
out.write("</h2>\r\n        </div>\r\n        <div class=\"d-grid gap-2\">\r\n            <a class=\"cmp-item__button mt-2 btn btn-lg text-white fontSize\" href=\"#\" role=\"button\">\r\n                See details\r\n            </a>\r\n        </div>\r\n    </div>\r\n\r\n</div>\r\n");
{
    Object var_templatevar5 = renderContext.getObjectModel().resolveProperty(_global_placeholdertemplate, "placeholder");
    {
        boolean var_templateoptions6_field$_isempty = (!renderContext.getObjectModel().toBoolean(_dynamic_hascontent));
        {
            java.util.Map var_templateoptions6 = obj().with("isEmpty", var_templateoptions6_field$_isempty);
            callUnit(out, renderContext, var_templatevar5, var_templateoptions6);
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

