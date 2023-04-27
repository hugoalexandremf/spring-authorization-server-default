/*FetchAPIUtils*/
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.min.css';
//var httpResponseStatus = undefined;
var httpResponseDataToReturn = undefined;

toast.configure({
     autoClose: 7500,
     draggable: false,
     position: "top-center",
     closeOnClick: true
});

export async function fetchForWS(url, httpMethod, body = "{}", credentials = "same-origin", headers = {
     //'Content-Type': 'application/x-www-form-urlencoded',
     "Content-Type": "application/json",
     "Accept": "application/json"
}) {
     var httpRequestElements = {
          credentials: credentials,
          method: httpMethod,
          headers: headers
     };
     if (httpMethod === "POST") {
          httpRequestElements.body = JSON.stringify(body);
     }
     try{
          let response = await fetch(url, httpRequestElements);
          httpResponseDataToReturn = {
               httpResponseStatus: response.status
          };
          let responseText = await response.text();
          httpResponseDataToReturn.responseData=responseText.length ? JSON.parse(responseText) : null;
     } catch(error){
          httpResponseDataToReturn = {
               httpResponseStatus: 500,
               responseData: error
          };
     }
     return httpResponseDataToReturn;
}

export function notifyUser(message, type, z_index, timeout) {
     /*$.notify({
         message: message
     }, {
         type: type,
         z_index: z_index,
         delay: timeout
     });*/
     /*setTimeout(function () {
         $.notifyClose();
     }, timeout);*/

     let options = {
          position: "top-right",
          autoClose: true,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          transtion: "slide",
          className: "notifyToUserBox",
          theme: "colored"
     };
     switch(type){
          case "success":
               toast.success(message, options);
               break;
          case "info":
               toast.info(message, options);
               break;
          case "warning":
               toast.warning(message, options);
               break;
          case "error":
               toast.error(message, options);
               break;
          default:
               toast.info(message, options);
               break;
     }
}

/*FetchAPIUtils*/

export function animateStatus(elem) {
     var element = "#" + elem;
     var originalFontSize = $(element).css("font-size");
     $(element).animate({
          fontSize: "100px"
     }, 850, function () {
     });

     $(element).animate({
          fontSize: originalFontSize
     });
}