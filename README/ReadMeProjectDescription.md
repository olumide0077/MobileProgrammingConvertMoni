# MobileProgrammingConvertMoni
The currency conversion app named ConvertMoni was created to use live API to get the current exchange rates of currencies.
It was built using two edit text and two spinners. The first edit text is for the entry of the amount to be converted while 
the second is for the converted equivalent in the other currency. While the first spinner is for the base currency selection,
the second one is to select the currency to which we are converting. Coroutine dependencies were added to the gradle. 
Network access permission too was added to the manifest. Also, the different resources including string resources too were used.
The API used is accessible at 
"https://api.apilayer.com/exchangerates_data/convert?to=$to&amp;from=$from&amp;amount=$amount&amp;apikey=$key&quot;,
where $to, $from and $amount are place holders for variables storing the base currency, converted to currency and amount to be 
converted respectively. And the $key is the personal API key obtainable by account holders with the API server.
