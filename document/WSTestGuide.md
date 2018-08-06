启动webservice服务地址：    
http://localhost:8090/demo/ws/countries.wsdl   
测试请求地址：   
http://localhost:8090/demo/ws   
测试请求body内容：  
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"  
   xmlns:gs="http://com.zyj.springboot.demo/ws">  
    <soapenv:Header/>  
    <soapenv:Body>  
       <gs:getCountryRequest>   
          <gs:name>Poland</gs:name>   
       </gs:getCountryRequest>   
   </soapenv:Body>   
</soapenv:Envelope>  