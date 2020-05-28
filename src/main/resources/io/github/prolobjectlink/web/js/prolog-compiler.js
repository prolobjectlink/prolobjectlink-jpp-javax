/*-
 * #%L
 * prolobjectlink-jpp-javax
 * %%
 * Copyright (C) 2020 Prolobjectlink Project
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */

function compileAndWrite(prologCode) {
    YP.see(new YP.StringReader(prologCode));
    var output = new YP.StringWriter();
    YP.tell(output);
    var TermList = new Variable();
    var PseudoCode = new Variable();
    for each (var l1 in parseInput(TermList)) {
        for each (var l2 in makeFunctionPseudoCode
                  (TermList, PseudoCode))
            convertFunctionJavascript(PseudoCode);
    }
    YP.seen();
    YP.told();
    document.write
      (output.toString().replace
       (/\n/g,"<br>").replace(/ /g,"&nbsp;"));
}


