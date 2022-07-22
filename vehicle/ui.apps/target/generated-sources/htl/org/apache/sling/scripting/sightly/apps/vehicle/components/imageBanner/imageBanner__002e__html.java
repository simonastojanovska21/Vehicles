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
package org.apache.sling.scripting.sightly.apps.vehicle.components.imageBanner;

import java.io.PrintWriter;
import java.util.Collection;
import javax.script.Bindings;

import org.apache.sling.scripting.sightly.render.RenderUnit;
import org.apache.sling.scripting.sightly.render.RenderContext;

public final class imageBanner__002e__html extends RenderUnit {

    @Override
    protected final void render(PrintWriter out,
                                Bindings bindings,
                                Bindings arguments,
                                RenderContext renderContext) {
// Main Template Body -----------------------------------------------------------------------------

Object _global_banner = null;
Object _global_placeholdertemplate = null;
Object _dynamic_hascontent = bindings.get("hascontent");
_global_banner = renderContext.call("use", com.vehicle.core.models.ImageBanner.class.getName(), obj());
_global_placeholdertemplate = renderContext.call("use", "core/wcm/components/commons/v1/templates.html", obj());
out.write("<div class=\"cmp-banner\">\r\n\r\n    <div class=\"card\">\r\n\r\n        <div class=\"cmp-banner__image d-block w-100 card-img\">");
{
    Object var_resourcecontent0 = renderContext.call("includeResource", ".", obj().with("resourceType", "vehicle/components/image"));
    out.write(renderContext.getObjectModel().toString(var_resourcecontent0));
}
out.write("</div>\r\n\r\n        <div class=\"card-img-overlay mt-5 text-center\">\r\n            <h1 class=\"fontSize mb-4 mt-5 text-white\">");
{
    Object var_1 = renderContext.call("xss", renderContext.getObjectModel().resolveProperty(_global_banner, "bannerText"), "text");
    out.write(renderContext.getObjectModel().toString(var_1));
}
out.write("</h1>\r\n\r\n            <a class=\" mt-2 btn btn-lg text-white fontSize\"");
{
    Object var_attrvalue2 = renderContext.call("uriManipulation", renderContext.getObjectModel().resolveProperty(_global_banner, "buttonRedirect"), obj().with("extension", "html"));
    {
        Object var_attrcontent3 = renderContext.call("xss", var_attrvalue2, "uri");
        {
            boolean var_shoulddisplayattr5 = (((null != var_attrcontent3) && (!"".equals(var_attrcontent3))) && ((!"".equals(var_attrvalue2)) && (!((Object)false).equals(var_attrvalue2))));
            if (var_shoulddisplayattr5) {
                out.write(" href");
                {
                    boolean var_istrueattr4 = (var_attrvalue2.equals(true));
                    if (!var_istrueattr4) {
                        out.write("=\"");
                        out.write(renderContext.getObjectModel().toString(var_attrcontent3));
                        out.write("\"");
                    }
                }
            }
        }
    }
}
out.write(" style=\"background-color: #B65040 !important;\" role=\"button\">");
{
    String var_6 = (("\r\n                " + renderContext.getObjectModel().toString(renderContext.call("xss", renderContext.getObjectModel().resolveProperty(_global_banner, "buttonText"), "text"))) + "\r\n            ");
    out.write(renderContext.getObjectModel().toString(var_6));
}
out.write("</a>\r\n\r\n        </div>\r\n\r\n    </div>\r\n</div>\r\n\r\n</div>\r\n");
{
    Object var_templatevar7 = renderContext.getObjectModel().resolveProperty(_global_placeholdertemplate, "placeholder");
    {
        boolean var_templateoptions8_field$_isempty = (!renderContext.getObjectModel().toBoolean(_dynamic_hascontent));
        {
            java.util.Map var_templateoptions8 = obj().with("isEmpty", var_templateoptions8_field$_isempty);
            callUnit(out, renderContext, var_templatevar7, var_templateoptions8);
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

