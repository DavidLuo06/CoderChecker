# CodeChecker
> a java code static analysis tools 

- **IDE**: Intellij
- **Build Tools**:  gradle
- **dependencies**: javaparser / jdt /junit / spotbugs

###usage
import this project to intellij with gradle format, then just run it
###result
main.html or main.xml
```html
<p id="N65839" style="display: none;">
<a href="#NP_CLOSING_NULL">Bug type NP_CLOSING_NULL (click for details)</a>
<br/>In class testcode.Test<br/>In method testcode.Test.main(String[])<br/>Value loaded from fileInputStream<br/>Dereferenced at Test.java:[line 40]</p>
</td>
</tr>
<tr class="tablerow0" onclick="toggleRow('N65904');">
<td>
<span class="priority-2">NP</span>
</td>
<td>errorFileInputStream is null guaranteed to be dereferenced in testcode.Test.main(String[]) on exception path</td>
</tr>
<tr class="detailrow0">
<td/>
<td>
<p id="N65904" style="display: none;">
<a href="#NP_GUARANTEED_DEREF_ON_EXCEPTION_PATH">Bug type NP_GUARANTEED_DEREF_ON_EXCEPTION_PATH (click for details)</a>
<br/>In class testcode.Test<br/>In method testcode.Test.main(String[])<br/>Value loaded from errorFileInputStream<br/>Dereferenced at Test.java:[line 45]<br/>Dereferenced at Test.java:[line 45]<br/>Null value at Test.java:[line 36]<br/>Known null at Test.java:[line 38]</p>
</td>
</tr>
</table>
<h2>
```