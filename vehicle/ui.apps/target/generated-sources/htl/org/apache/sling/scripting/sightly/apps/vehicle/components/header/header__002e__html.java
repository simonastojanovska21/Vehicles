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
package org.apache.sling.scripting.sightly.apps.vehicle.components.header;

import java.io.PrintWriter;
import java.util.Collection;
import javax.script.Bindings;

import org.apache.sling.scripting.sightly.render.RenderUnit;
import org.apache.sling.scripting.sightly.render.RenderContext;

public final class header__002e__html extends RenderUnit {

    @Override
    protected final void render(PrintWriter out,
                                Bindings bindings,
                                Bindings arguments,
                                RenderContext renderContext) {
// Main Template Body -----------------------------------------------------------------------------

Object _global_placeholdertemplate = null;
out.write("\r\n");
_global_placeholdertemplate = renderContext.call("use", "core/wcm/components/commons/v1/templates.html", obj());
out.write("<div class=\"cmp-header\">\r\n\r\n    <div class=\"container-fluid\">\r\n\r\n        <div class=\"row\">\r\n            <div class=\"col-md-4\">\r\n                <img src=\"/apps/vehicle/components/header/clientlib/resources/carLogo.png\" class=\"img-fluid cmp-header__image\"/>\r\n            </div>\r\n\r\n            <div class=\"col-sm-8\">\r\n                ");
{
    Object var_resourcecontent0 = renderContext.call("includeResource", "./navigation", obj().with("decoration", true));
    out.write(renderContext.getObjectModel().toString(var_resourcecontent0));
}
out.write("\r\n            </div>\r\n        </div>\r\n\r\n    </div>\r\n\r\n</div>\r\n");
{
    Object var_templatevar1 = renderContext.getObjectModel().resolveProperty(_global_placeholdertemplate, "placeholder");
    {
        boolean var_templateoptions2_field$_isempty = true;
        {
            java.util.Map var_templateoptions2 = obj().with("isEmpty", var_templateoptions2_field$_isempty);
            callUnit(out, renderContext, var_templatevar1, var_templateoptions2);
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

