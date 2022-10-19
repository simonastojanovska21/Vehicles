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
package org.apache.sling.scripting.sightly.apps.vehicle.components.addNewCar;

import java.io.PrintWriter;
import java.util.Collection;
import javax.script.Bindings;

import org.apache.sling.scripting.sightly.render.RenderUnit;
import org.apache.sling.scripting.sightly.render.RenderContext;

public final class addNewCar__002e__html extends RenderUnit {

    @Override
    protected final void render(PrintWriter out,
                                Bindings bindings,
                                Bindings arguments,
                                RenderContext renderContext) {
// Main Template Body -----------------------------------------------------------------------------

Object _global_placeholdertemplate = null;
Object _dynamic_hascontent = bindings.get("hascontent");
_global_placeholdertemplate = renderContext.call("use", "core/wcm/components/commons/v1/templates.html", obj());
out.write("<div id=\"addNewCar\">\r\n\r\n    <div class=\"container mt-5 mb-5\">\r\n        <div class=\"mx-auto w-50\">\r\n\r\n            <h1 class=\"title text-center\">Add new car</h1>\r\n            <hr/>\r\n\r\n            <div class=\"row g-3\">\r\n\r\n                <div class=\"col-md-12\">\r\n                    <label for=\"carBrand\" class=\"form-label cmp-addCar__label\">Brand</label>\r\n                    <select id=\"carBrand\" name=\"carBrand\" class=\"form-control\">\r\n                        <option value=\"Select\">Select</option>\r\n                    </select>\r\n                </div>\r\n\r\n                <div class=\"col-md-12\">\r\n                    <label for=\"carModel\" class=\"form-label cmp-addCar__label\">Model</label>\r\n                    <select id=\"carModel\" name=\"carModel\" class=\"form-control\" disabled>\r\n                        <option value=\"Select\">Select</option>\r\n                    </select>\r\n                </div>\r\n\r\n                <div class=\"col-md-12\">\r\n                    <label for=\"imageUrl\" class=\"form-label cmp-addCar__label\">Image URL</label>\r\n                    <input type=\"text\" class=\"form-control\" id=\"imageUrl\" name=\"imageUrl\" placeholder=\"Enter the image URL for the car\" required/>\r\n                </div>\r\n\r\n                <div class=\"col-md-12\">\r\n                    <label for=\"year\" class=\"form-label cmp-addCar__label\">Year</label>\r\n                    <input type=\"number\" class=\"form-control\" id=\"year\" name=\"year\" placeholder=\"Enter the year of the car\" required/>\r\n                </div>\r\n\r\n                <div class=\"col-md-12\">\r\n                    <label for=\"kilometers\" class=\"form-label cmp-addCar__label\">Kilometers</label>\r\n                    <input type=\"number\" class=\"form-control\" id=\"kilometers\" name=\"kilometers\" placeholder=\"Enter the kilometers of the car\" required/>\r\n                </div>\r\n\r\n                <div class=\"col-md-12\">\r\n                    <label for=\"transmission\" class=\"form-label cmp-addCar__label\">Transmission</label>\r\n                    <div id=\"transmission\">\r\n\r\n                    </div>\r\n                </div>\r\n\r\n                <div class=\"col-md-12\">\r\n                    <label for=\"bodyStyle\" class=\"form-label cmp-addCar__label\">Body style</label>\r\n                    <div id=\"bodyStyle\">\r\n\r\n                    </div>\r\n                </div>\r\n\r\n                <div class=\"d-grid gap-2 col-md-12 mt-4\">\r\n                    <button id=\"submitNewCar\" onclick=\"addNewCar(this)\" class=\"btn btn-block text-white\" style=\"background-color: #B65040 !important;\">\r\n                        Add\r\n                    </button>\r\n                </div>\r\n            </div>\r\n        </div>\r\n    </div>\r\n\r\n</div>\r\n\r\n</div>\r\n");
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

