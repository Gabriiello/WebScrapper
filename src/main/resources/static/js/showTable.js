
	loadProducts()





async function loadProducts(){





    const request = await fetch('/product/getproductSave', {
        method: 'GET', 
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        }
      });
      const products = await request.json();
    
      console.log(products);

    let tablaLlena= '';
    for(product of products){
        let btEliminar= '<a href="#" onclick="eliminarProducto('+product.id+ ')" class="btn btn-danger btn-circle"><i class="fas fa-trash">X</i></a>'
        let linkProduct= '<a href="'+product.url+'" target="_blank">See Product</a>'
        let productIterado= '<tr><th scope="row">'+product.id+'</th><td>'+product.nombre+'</td><td>'+product.precio+'</td><td>'+linkProduct+'</td><td>'+btEliminar+'</td></tr>'
        tablaLlena += productIterado

    }

    document.querySelector('#Products tbody').outerHTML= tablaLlena


}


async function eliminarProducto(id){
	
	if(!confirm("Esta seguro de eliminar este usuario")){
		return
	}
	
	const request = await fetch('/product/deleteProduct/'+id, {
    method: 'DELETE'
    
  });
  
  location.reload()
}