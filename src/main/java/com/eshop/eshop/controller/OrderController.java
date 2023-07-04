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

import com.eshop.eshop.dao.OrderDao;
import com.eshop.eshop.entity.Orders;
import com.eshop.eshop.entity.HttpResponse;

@RestController // precise que c'est une route pour une api AV
@RequestMapping // precise que c'est une route api AV
@CrossOrigin("*") // Permet que les serveur comunique entre eux AV
public class OrderController {

    @Autowired // liez le controller a sa DAO AV
    OrderDao orderDao;
    private static final String ORDER_DELETED_SUCCESSFULLY = "order deleted successfully"; // confirme que j'ai effacer
                                                                                           // order c'est juste le
                                                                                           // message

    // Recupere une liste categories
    @GetMapping("/orders")
    // @PreAuthorize("hasAnyAuthority('admin:read')") // Verifie les droits de la
    // personne qui effectue la requete
    public List<Orders> getAllOrders() {
        return orderDao.getOrders();

    }

    // Crée une categorie
    @PostMapping("/orders")
    // @PreAuthorize("hasAnyAuthority('admin:create')")
    public Orders createOrder(@RequestBody Orders order) {
        return orderDao.saveOrder(order);

    }

    // recupere une categorie via son ID
    @GetMapping("/orders/{idOrder}")
    // @PreAuthorize("hasAnyAuthority('admin:read')")
    public ResponseEntity findOrderById(@PathVariable(name = "idOrder") Long idOrder) {
        if (idOrder == null) {
            return ResponseEntity.badRequest().body("Je ne trouve pas le produit avec son ID");
        }

        Orders order = orderDao.getOrderById(idOrder);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(order);
    }

    // Update une order via son ID
    @PutMapping("/orders/{idOrder}")
    // @PreAuthorize("hasAnyAuthority('admin:update')")
    public ResponseEntity<Orders> updateOrder(@Validated @PathVariable(name = "idOrder") Long id,
            @RequestBody(required = false) Orders order) {

        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        order.setIdOrder(id);
        orderDao.updateOrder(order);
        return ResponseEntity.ok().body(order);
    }

    // delete
    @DeleteMapping("/orders/{idOrder}")
    // @PreAuthorize("hasAnyAuthority('admin:delete')")
    public ResponseEntity<HttpResponse> deleteOrder(@Validated @PathVariable(name = "idOrder") Long id) {
        orderDao.deleteOrder(id);
        return response(HttpStatus.OK, ORDER_DELETED_SUCCESSFULLY + id);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String order) {

        HttpResponse body = new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(), order.toUpperCase());

        return new ResponseEntity<>(body, httpStatus);
    }
}
