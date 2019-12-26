package com.example.services;

import com.example.models.Customer;
import com.example.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;


@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void findAllCustomers() {

        List<Customer> returned1=new ArrayList<>();
        returned1.add(new Customer(1L,"ajay"));
        returned1.add(new Customer(2L,"ravi"));

        Mockito.when(customerRepository.findAll()).thenReturn(returned1);

        List<Customer> result=customerService.findAllCustomers();
        Assert.assertEquals("ajay",result.get(0).getName());
    }

    @Test(expected = EntityNotFoundException.class)
    public void findAllCustomers_notFound() {

        Mockito.when(customerRepository.findAll()).thenReturn(null);
        customerService.findAllCustomers();

    }

        @Test
    public void getCustomer() {

        Customer customer= new Customer(1L, "rahul");
        //stub the data
        Mockito.when(customerRepository.getOne(1L)).thenReturn(customer);

        Customer result = customerService.getCustomer(1L);
        Assert.assertEquals("rahul", result.getName());

    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetCustomer_notFound(){

        Mockito.when(customerRepository.getOne(1L)).thenReturn(null);
        customerService.getCustomer(1L);
    }

    @Test
    public void saveCustomer() throws Exception{


        Customer input=new Customer(1L,"ajay");

        Customer returned1=new Customer(1L,"ajay");

        Mockito.when(customerRepository.save(input)).thenReturn(returned1);

        Customer result=customerService.saveCustomer(input);

        Assert.assertEquals("ajay", result.getName());


    }

    @Test
    public void getByName() {

        String input1="ravi";
        Customer returned1=new Customer(1L,"ravi");

        Mockito.when(customerRepository.searchByName("ravi")).thenReturn(returned1);

        Customer result=customerService.getByName("ravi");
        Assert.assertEquals("ravi",result.getName());
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByName_notFound(){
        Mockito.when(customerRepository.findByName("ravi")).thenReturn(null);
        customerService.getByName("ravi");
    }

    @Test
    public void updateCustomer() {

        Customer input1=new Customer(1L,"ajay");
        Customer returned1=new Customer(1l,"ravi");

        Mockito.when(customerRepository.getOne(1L)).thenReturn(input1);
        Mockito.when(customerRepository.save(input1)).thenReturn(returned1);

        Customer result=customerService.updateCustomer(input1.getId(),input1);

        Assert.assertEquals("ravi", result.getName());


    }


    @Test(expected = EntityNotFoundException.class)
    public void updateCustomer_notFound() {

        Mockito.when(customerRepository.getOne(1L)).thenReturn(null);
        Customer input1=new Customer(1L,"ajay");
        customerService.updateCustomer(1L,input1);

    }

    @Test
    public void deleteCustomer() {
    Mockito.doNothing().when(customerRepository).deleteById(1L);
    customerService.deleteCustomer(1L);
    }
}