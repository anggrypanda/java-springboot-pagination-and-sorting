package ro.nexttech.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ro.nexttech.springboot.model.Invoice;
import ro.nexttech.springboot.service.InvoiceService;

@Controller
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        return findPaginated(1, "invoiceId", "asc", model);
    }

    @GetMapping("/createInvoiceForm")
    public String createInvoiceForm(Model model) {

        Invoice invoice = new Invoice();
        model.addAttribute("invoice", invoice);
        return "create_invoice";
    }

    @PostMapping("/createInvoice")
    public String createInvoice(@ModelAttribute("invoice") Invoice invoice) {

        invoiceService.createInvoice(invoice);
        return "redirect:/";
    }

    @GetMapping("/updateInvoice/{id}")
    public String updateInvoice(@PathVariable ( value = "id") long id, Model model) {

        Invoice invoice = invoiceService.getInvoiceById(id);

        model.addAttribute("invoice", invoice);
        return "update_invoice";
    }

    @GetMapping("/deleteInvoice/{id}")
    public String deleteInvoice(@PathVariable (value = "id") long id) {

        this.invoiceService.deleteInvoiceById(id);
        return "delete_invoice";
    }


    @GetMapping("/page/{pageNr}")
    public String findPaginated(@PathVariable (value = "pageNr") int pageNr,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page<Invoice> page = invoiceService.findPaginated(pageNr, pageSize, sortField, sortDir);
        List<Invoice> listInvoices = page.getContent();

        model.addAttribute("currentPage", pageNr);
        model.addAttribute("firstPage", pageNr);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listInvoices", listInvoices);
        return "index";
    }
}