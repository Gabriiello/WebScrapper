
async function searchProducts(){
    
  let busqueda= document.getElementById('txtBusqueda').value


	
  const request = await fetch('/home/search/'+busqueda, {
    method: 'GET'
  });



  window.location.href= "/producto"
   
}

 function lessP(){
	localStorage.prefe= "less";
}

function higherP(){
	localStorage.prefe= "higher";
}
