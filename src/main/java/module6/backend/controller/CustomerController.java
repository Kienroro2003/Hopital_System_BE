package module6.backend.controller;


import module6.backend.entity.customer.Customer;
import module6.backend.entity.customer.CustomerType;
import module6.backend.repository.ICartRepository;
import module6.backend.repository.ICustomerRepository;
import module6.backend.service.ICustomerTypeService;
import module6.backend.service.Impl.CartMaterialServiceImpl;
import module6.backend.service.Impl.CartServiceImpl;
import module6.backend.service.Impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/customer")
public class CustomerController {
    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private ICustomerRepository customerRepository;

    @Autowired
    private CartServiceImpl cartService;

    @Autowired
    private ICustomerTypeService customerTypeService;


    @Autowired
    private CartMaterialServiceImpl cartMaterialService;

    @Autowired
    private ICartRepository cartRepository;


    //  HieuNT  get list customer  not pagination
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT', 'ROLE_SELL')")
    @GetMapping("")
    public ResponseEntity<List<Customer>> getAllCustomer() {
        List<Customer> customerList = this.customerService.getAllCustomer();
        if (customerList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    /// HieuNT get list with pagination
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT', 'ROLE_SELL')")
    @GetMapping("/customer-pagination/{index}")
    public ResponseEntity<List<Customer>> getAllCustomer(@PathVariable("index") int index) {
        List<Customer> customers = customerService.getAllCustomerWithPagination(index);
        if (customers.isEmpty()) {
            return new ResponseEntity<List<Customer>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
    }


    //  HieuNT  delete customer
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT', 'ROLE_SELL')")
    @DeleteMapping("/customer-delete/{id}")
    public ResponseEntity<Customer> delete(@PathVariable("id") Long id) {
        Optional<Customer> customerOptional = customerService.findCustomerById(id);
        System.out.println(2);
        if (!customerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customerService.deleteCustomerById(-id, id);
        return new ResponseEntity<>(customerOptional.get(), HttpStatus.NO_CONTENT);
    }

    //   HieuNT search customer by name and phone
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT', 'ROLE_SELL')")
    @GetMapping(value = "/search-customer")
    public ResponseEntity<List<Customer>> searchCustomerByNameAndPhone(@RequestParam("name") String name, @RequestParam("phone") String phone) {
        List<Customer> isCustomerExist = customerService.searchCustomerByNameAndPhone(name, phone);


        if (isCustomerExist != null) {
            return new ResponseEntity<>(isCustomerExist, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    //  SonLH  Tìm kiếm khách hàng theo id
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT', 'ROLE_SELL')")
    @GetMapping("/detail/{id}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable Long id) {
        Optional<Customer> customerOptional = customerService.findCustomerById(id);
        if (!customerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customerOptional.get(), HttpStatus.OK);
    }

    //  SonLH lấy danh dách loại khách hàng
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT', 'ROLE_SELL')")
    @GetMapping("/customer-type")
    public ResponseEntity<List<CustomerType>> findAllCustomerType() {
        List<CustomerType> customerTypes = customerTypeService.findAllCustomerType();
        if (customerTypes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customerTypes, HttpStatus.OK);
    }

    // DuyDTT tạo mới khách hàng
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT', 'ROLE_SELL')")
    @PostMapping("/customer-create")
    public ResponseEntity<Customer> createNewCustomer(@RequestBody Customer customer) {
        System.out.println(0);
        customerService.createCustomer(customer.getCustomerName(), customer.getCustomerCode(), customer.getCustomerAvatar(), customer.getCustomerAddress(), customer.getCustomerPhone(), customer.getCustomerEmail(), customer.getCustomerTypeId().getCustomerTypeId());
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    // DuyDTT cập nhật khách hàng
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT', 'ROLE_SELL')")
    @PatchMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        Optional<Customer> customerOptional = customerService.findCustomerById(customer.getCustomerId());
        if (!customerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customerService.updateCustomer(customer.getCustomerName(), customer.getCustomerCode(), customer.getCustomerAvatar(), customer.getCustomerAddress(), customer.getCustomerPhone(), customer.getCustomerEmail(), customer.getCustomerTypeId().getCustomerTypeId(), customer.getCustomerId());
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}
