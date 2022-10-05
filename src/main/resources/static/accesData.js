cargarDatosProduct();
let producto


  async function cargarDatosProduct(){
	 
   let metodo= (localStorage.prefe=="less")?'/search/lessProductPrice':'/search/higherProductPrice';


  const request = await fetch(metodo, {
    method: 'GET', 
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    }
  });
  const products = await request.json();

  console.log(products);
  
  let infor= '';
  for(let product of products){
    producto= product
	 
	 let btAgregar= '<span><form class="Product"><button href="#" onclick="saveProduct()">Guardar Producto</button></form></span>';
	 let iteradoinfor= '<div class="row g-0"><div class="col-md-4"><img src="'+product.urlImage+'" class="img-fluid rounded-start" alt="..."></div><div class="col-md-8"><div class="card-body"><h5 class="card-title">'+product.nombre+'</h5><p class="card-text">'+product.descripcion+'</p><h3>'+product.precio+'</h3><p class="card-text"><small class="text-muted">'+product.url+'</small></p></div></div>'+btAgregar+'</div>'
	
	 infor += iteradoinfor 
  }
  
  console.log(infor);
  

  document.querySelector('div.card.mb-3').outerHTML= infor
  
  
}


async function saveProduct(){
   let productToSave= {}
   productToSave.nombre= producto.nombre
   productToSave.precio= producto.precio
   productToSave.url= producto.url
 const request = await fetch('/product/saveProduct', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(productToSave)
  });
  
  alert("Se ha guardado el producto!");

}




