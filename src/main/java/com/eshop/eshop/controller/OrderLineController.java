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

import com.eshop.eshop.dao.OrderLineDao;
import com.eshop.eshop.entity.HttpResponse;
import com.eshop.eshop.entity.OrderLine;

@RestController //precise que c'est une route pour une api AV
@RequestMapping // precise que c'est une route api  AV
@CrossOrigin("*")//Permet que les serveur comunique entre eux AV 
public class OrderLineController {
    @Autowired // liez le controller a sa DAO AV 
  OrderLineDao orderLineDao ;
  private static final String ORDERLINE_DELETED_SUCCESSFULLY = "orderLine deleted successfully"; // confirme que j'ai effacer orderLine c'est juste le message 

 //Recupere une liste orderLines	
  @GetMapping("/orderLines")
  //@PreAuthorize("hasAnyAuthority('admin:read')") // Verifie les droits de la personne qui effectue la requete
  public List<OrderLine> getAllOrderLines() {
      return orderLineDao.getOrderLines();

  }
//Crée une orderLine 
  @PostMapping("/orderLines")
  //@PreAuthorize("hasAnyAuthority('admin:create')")
  public OrderLine createOrderLine(@RequestBody OrderLine orderLine) {
      return orderLineDao.saveOrderLine(orderLine);

  }
//recupere une orderLine via son ID
  @GetMapping("/orderLines/{idOrderLine}")
 // @PreAuthorize("hasAnyAuthority('admin:read')")
  public ResponseEntity findOrderLineById(@PathVariable(name = "idOrderLine")Long idOrderLine){
      if (idOrderLine == null) {
          return ResponseEntity.badRequest().body("Je ne trouve pas le produit avec son ID");
      }

      OrderLine orderLine = orderLineDao.getOrderLineById(idOrderLine);

      if (orderLine == null) {
          return ResponseEntity.notFound().build();
      }

      return ResponseEntity.ok().body(orderLine); }
//Update une orderLine via son ID
  @PutMapping("/orderLines/{idOrderLine}")
  //@PreAuthorize("hasAnyAuthority('admin:update')")
  public ResponseEntity<OrderLine> updateOrderLine(@Validated @PathVariable(name="idOrder")Long id, @RequestBody(required = false) OrderLine orderLine) {

      if(orderLine==null) {
          return ResponseEntity.notFound().build();
      }
      orderLine.setIdOrderLine(id);
      orderLineDao.updateOrderLine (orderLine);
      return ResponseEntity.ok().body(orderLine);
  }
  //delete 
  @DeleteMapping("/orderLines/{idOrderLine}")
  //@PreAuthorize("hasAnyAuthority('admin:delete')")
  public ResponseEntity<HttpResponse> deleteOrderLine(@Validated
          @PathVariable(name = "idOrderLine")Long id){
              orderLineDao.deleteOrderLine(id);
              return response(HttpStatus.OK, ORDERLINE_DELETED_SUCCESSFULLY + id);
  }
  private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String orderLine) {

      HttpResponse body = new HttpResponse(httpStatus.value(), httpStatus,
              httpStatus.getReasonPhrase().toUpperCase(), orderLine.toUpperCase());

      return new ResponseEntity<>(body, httpStatus);
  }
}
