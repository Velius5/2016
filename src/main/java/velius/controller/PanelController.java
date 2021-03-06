/*
 * Author: Patryk Dobrzyński
 * Author URL: http://patrykdobrzynski.eu
 * Author Email: kontakt@patrykdobrzynski.eu
 */
package velius.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import velius.model.Product;
import velius.model.Receipt;
import velius.model.User;
import velius.service.ProductService;
import velius.service.ReceiptService;
import velius.service.UserService;

/**
 * Kontroler obsługujący zapytania wysyłane przez przeglądarkę po zalogowaniu 
 * się przez użytkownika. Dostarcza dane do widoku strony głównej dla 
 * zalogowanych użytkowników
 */
@Controller
@RequestMapping("/panel")
public class PanelController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    ReceiptService receiptService;
    
    @Autowired
    ProductService productService;
    
    @RequestMapping("")
    public String panelPage(Model model, Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.getUserByEmail(email);
        model.addAttribute("userName", user.getName());
        
        List<Receipt> receiptList = receiptService.findLast6ByOwner(user);
        model.addAttribute("paragony", receiptList);
        
        List<Product> debtorList = productService.getUserDebitors(user);
        model.addAttribute("produktyDluznikow", debtorList);
        
        List<Product> myDebts = productService.getMyDebts(user);
        model.addAttribute("mojeDlugi", myDebts);
        
        BigDecimal saldo = BigDecimal.ZERO;
        
        for (Product prod : debtorList) {
            saldo = saldo.add(prod.getPricePerPerson());
        }
        int incoming = saldo.intValue();
        for (Product prod : myDebts) {
            saldo = saldo.subtract(prod.getPricePerPerson());
        }
        int outcoming = incoming - saldo.intValue();
        model.addAttribute("bilans", saldo+" zł");
        model.addAttribute("przychody",incoming);
        model.addAttribute("wydatki", outcoming);
        return "panel";
    }
    
    @RequestMapping(value = "/product/paid/{prodId}/{payerId}",method = RequestMethod.GET)
    public String productPaid(Model model, Principal principal,@PathVariable Long prodId,@PathVariable Long payerId){
        User user = userService.getUser(payerId);
        List<Product> productList = user.getProducts();
        
        Product prod = productService.getProduct(prodId);
        productList.remove(prod);
        user.setProducts(productList);
        List<Product> history = user.getProductsHistory();
        history.add(prod);
        user.setProductsHistory(history);
        
        userService.save(user);
        return "redirect:/panel";
    }
    
    
}
