<br />
<font size='1'><table class='xdebug-error xe-fatal-error' dir='ltr' border='1' cellspacing='0' cellpadding='1'>
<tr><th align='left' bgcolor='#f57900' colspan="5"><span style='background-color: #cc0000; color: #fce94f; font-size: x-large;'>( ! )</span> Fatal error: Allowed memory size of 134217728 bytes exhausted (tried to allocate 52473856 bytes) in C:\wamp\apps\phpmyadmin4.0.4\libraries\Response.class.php on line <i>273</i></th></tr>
<tr><th align='left' bgcolor='#e9b96e' colspan='5'>Call Stack</th></tr>
<tr><th align='center' bgcolor='#eeeeec'>#</th><th align='left' bgcolor='#eeeeec'>Time</th><th align='left' bgcolor='#eeeeec'>Memory</th><th align='left' bgcolor='#eeeeec'>Function</th><th align='left' bgcolor='#eeeeec'>Location</th></tr>
<tr><td bgcolor='#eeeeec' align='center'>1</td><td bgcolor='#eeeeec' align='center'>0.0103</td><td bgcolor='#eeeeec' align='right'>477120</td><td bgcolor='#eeeeec'>{main}(  )</td><td title='C:\wamp\apps\phpmyadmin4.0.4\export.php' bgcolor='#eeeeec'>..\export.php<b>:</b>0</td></tr>
<tr><td bgcolor='#eeeeec' align='center'>2</td><td bgcolor='#eeeeec' align='center'>41.5457</td><td bgcolor='#eeeeec' align='right'>16312024</td><td bgcolor='#eeeeec'>ExportSql->exportData(  )</td><td title='C:\wamp\apps\phpmyadmin4.0.4\export.php' bgcolor='#eeeeec'>..\export.php<b>:</b>765</td></tr>
<tr><td bgcolor='#eeeeec' align='center'>3</td><td bgcolor='#eeeeec' align='center'>301.2754</td><td bgcolor='#eeeeec' align='right'>62096872</td><td bgcolor='#eeeeec'>PMA_Util::sqlAddSlashes(  )</td><td title='C:\wamp\apps\phpmyadmin4.0.4\libraries\plugins\export\ExportSql.class.php' bgcolor='#eeeeec'>..\ExportSql.class.php<b>:</b>1774</td></tr>
<tr><td bgcolor='#eeeeec' align='center'>4</td><td bgcolor='#eeeeec' align='center'>301.5494</td><td bgcolor='#eeeeec' align='right'>62093080</td><td bgcolor='#eeeeec'>PMA_OutputBuffering::stop(  )</td><td title='C:\wamp\apps\phpmyadmin4.0.4\libraries\OutputBuffering.class.php' bgcolor='#eeeeec'>..\OutputBuffering.class.php<b>:</b>0</td></tr>
<tr><td bgcolor='#eeeeec' align='center'>5</td><td bgcolor='#eeeeec' align='center'>301.6191</td><td bgcolor='#eeeeec' align='right'>62092520</td><td bgcolor='#eeeeec'>PMA_Response::response(  )</td><td title='C:\wamp\apps\phpmyadmin4.0.4\libraries\OutputBuffering.class.php' bgcolor='#eeeeec'>..\OutputBuffering.class.php<b>:</b>117</td></tr>
<tr><td bgcolor='#eeeeec' align='center'>6</td><td bgcolor='#eeeeec' align='center'>301.6197</td><td bgcolor='#eeeeec' align='right'>62092624</td><td bgcolor='#eeeeec'>PMA_Response->_htmlResponse(  )</td><td title='C:\wamp\apps\phpmyadmin4.0.4\libraries\Response.class.php' bgcolor='#eeeeec'>..\Response.class.php<b>:</b>370</td></tr>
</table></font>
