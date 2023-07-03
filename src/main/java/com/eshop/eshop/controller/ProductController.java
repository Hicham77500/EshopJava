package com.eshop.eshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.eshop.dao.ProductDao;
import com.eshop.eshop.entity.HttpResponse;
import com.eshop.eshop.entity.Product;

@RestController //precise que c'est une route pour une api AV
@RequestMapping // precise que c'est une route api  AV
@CrossOrigin("*")//Permet que les serveur comunique entre eux AV 
public class ProductController {
    @Autowired // liez le controller a sa DAO AV 
    ProductDao productDao ;
    private static final String PRODUCT_DELETED_SUCCESSFULLY = "product deleted successfully"; // confirme que j'ai effacer product c'est juste le message 
  
   //Recupere une liste products	
    @GetMapping("/products")
    //@PreAuthorize("hasAnyAuthority('admin:read')") // Verifie les droits de la personne qui effectue la requete
    public List<Product> getAllProducts() {
        return productDao.getProducts();
  
    }
  //Crée une product 
    @PostMapping("/products")
    //@PreAuthorize("hasAnyAuthority('admin:create')")
    public Product createProduct(Product product) {
        return productDao.saveProduct(product);
  
    }
  //recupere une product via son ID
    @GetMapping("/products/{idProduct}")
   // @PreAuthorize("hasAnyAuthority('admin:read')")
    public ResponseEntity findProductById(@PathVariable(name = "idProduct")Long idProduct){
        if (idProduct == null) {
            return ResponseEntity.badRequest().body("Je ne trouve pas le produit avec son ID");
        }
  
        Product product = productDao.getProductById(idProduct);
  
        if (product == null) { 
            return ResponseEntity.notFound().build();
        }
  
        return ResponseEntity.ok().body(product); }
  //Update une product via son ID
    @PutMapping("/products/{idProduct}")
    //@PreAuthorize("hasAnyAuthority('admin:update')")
    public ResponseEntity<Product> updateProduct(@Validated @PathVariable(name="idProduct")Long id, @RequestBody(required = false) Product product) {
  
        if(product==null) {
            return ResponseEntity.notFound().build();
        }
        product.setIdProd(id);
        productDao.updateProduct(product);
        return ResponseEntity.ok().body(product);
    }
    //delete 
    @DeleteMapping("/products/{idProduct}")
    //@PreAuthorize("hasAnyAuthority('admin:delete')")
    public ResponseEntity<HttpResponse> deleteProduct(@Validated
            @PathVariable(name = "idProduct")Long id){
                productDao.deleteProduct(id);
                return response(HttpStatus.OK, PRODUCT_DELETED_SUCCESSFULLY + id);
    }
    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String product) {
  
        HttpResponse body = new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(), product.toUpperCase());
  
        return new ResponseEntity<>(body, httpStatus);
    }
}
