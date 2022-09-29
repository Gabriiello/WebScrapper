
async function searchProducts(){
    
  let busqueda= document.getElementById('txtBusqueda').value


	
  const request = await fetch('/home/search/'+busqueda, {
    method: 'GET'
  });

  window.location.href= "productoCheck.html"
   
}

 function lessP(){
	localStorage.prefe= "less";
}

function higherP(){
	localStorage.prefe= "higher";
}
