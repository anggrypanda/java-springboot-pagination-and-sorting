package ro.nexttech.springboot.service;

import java.util.List;
import org.springframework.data.domain.Page;

import ro.nexttech.springboot.model.Invoice;

public interface InvoiceService {
    List<Invoice> getAllInvoices();
    void createInvoice(Invoice invoice);
    Invoice getInvoiceById(long id);
    void deleteInvoiceById(long id);
    Page<Invoice> findPaginated(int pageNr, int pageSize, String sortField, String sortDirection);
}