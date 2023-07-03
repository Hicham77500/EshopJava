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

import com.eshop.eshop.dao.CategoryDao;
import com.eshop.eshop.entity.Category;
import com.eshop.eshop.entity.HttpResponse;

@RestController //precise que c'est une route pour une api AV
@RequestMapping // precise que c'est une route api  AV
@CrossOrigin("*")//Permet que les serveur comunique entre eux AV 
public class CategoryController {
  @Autowired // liez le controller a sa DAO AV 
  CategoryDao categoryDao ;
  private static final String CATEGORY_DELETED_SUCCESSFULLY = "category deleted successfully"; // confirme que j'ai effacer category c'est juste le message 

 //Recupere une liste categories	
  @GetMapping("/categories")
  //@PreAuthorize("hasAnyAuthority('admin:read')") // Verifie les droits de la personne qui effectue la requete
  public List<Category> getAllCategories() {
      return categoryDao.getCategories();

  }
//Crée une categorie 
  @PostMapping("/categories")
  //@PreAuthorize("hasAnyAuthority('admin:create')")
  public Category createCategory(Category category) {
      return categoryDao.saveCategory(category);

  }
//recupere une categorie via son ID
  @GetMapping("/categories/{idCategory}")
 // @PreAuthorize("hasAnyAuthority('admin:read')")
  public ResponseEntity findCategoryById(@PathVariable(name = "idCategory")Long idCategory){
      if (idCategory == null) {
          return ResponseEntity.badRequest().body("Je ne trouve pas le produit avec son ID");
      }

      Category category = categoryDao.getCategoryById(idCategory);

      if (category == null) {
          return ResponseEntity.notFound().build();
      }

      return ResponseEntity.ok().body(category); }
//Update une category via son ID
  @PutMapping("/categories/{idCategory}")
  //@PreAuthorize("hasAnyAuthority('admin:update')")
  public ResponseEntity<Category> updateCategory(@Validated @PathVariable(name="idCategory")Long id, @RequestBody(required = false) Category category) {

      if(category==null) {
          return ResponseEntity.notFound().build();
      }
      category.setIdCat(id);
      categoryDao.updateCategory(category);
      return ResponseEntity.ok().body(category);
  }
  //delete 
  @DeleteMapping("/categories/{idCategory}")
  //@PreAuthorize("hasAnyAuthority('admin:delete')")
  public ResponseEntity<HttpResponse> deleteCategory(@Validated
          @PathVariable(name = "idCategory")Long id){
              categoryDao.deleteCategory(id);
              return response(HttpStatus.OK, CATEGORY_DELETED_SUCCESSFULLY + id);
  }
  private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String category) {

      HttpResponse body = new HttpResponse(httpStatus.value(), httpStatus,
              httpStatus.getReasonPhrase().toUpperCase(), category.toUpperCase());

      return new ResponseEntity<>(body, httpStatus);
  }
}
