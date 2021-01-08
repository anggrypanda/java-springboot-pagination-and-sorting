package ro.nexttech.springboot.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ro.nexttech.springboot.model.Invoice;
import ro.nexttech.springboot.repository.InvoiceRepository;
import ro.nexttech.springboot.service.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public void createInvoice(Invoice invoice) {
        this.invoiceRepository.save(invoice);
    }

    @Override
    public Invoice getInvoiceById(long id) {
        Optional<Invoice> optional = invoiceRepository.findById(id);
        Invoice invoice = null;
        if (optional.isPresent()) {
            invoice = optional.get();
        } else {
            throw new RuntimeException(" Invoice with id " + id + " not found.");
        }
        return invoice;
    }

    @Override
    public void deleteInvoiceById(long id) {
        this.invoiceRepository.deleteById(id);
    }

    @Override
    public Page<Invoice> findPaginated(int pageNr, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNr - 1, pageSize, sort);
        return this.invoiceRepository.findAll(pageable);
    }
}
