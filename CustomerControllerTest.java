package com.example.controller;

import com.example.models.Customer;
import com.example.services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerController.class, secure = false)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;


    @Test
    public void getAllCustomers() throws Exception {

        List<Customer> customers = new ArrayList<Customer>();

        customers.add(new Customer(101L, "ajay"));
        customers.add(new Customer(103L, "ravi"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/get/allcustomers").accept(
                MediaType.APPLICATION_JSON);

        Mockito.when(customerService.findAllCustomers()).thenReturn(customers);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();


        String expected= "[{id:103,name:ravi},{id:101,name:ajay}]";
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);



    }


    @Test
    public void getByName() throws Exception {

    String name = "ravi";
    Customer customer = new Customer(103L,"ravi");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/get/customer/name/"+name).accept(
                MediaType.APPLICATION_JSON);

        Mockito.when(customerService.getByName(name)).thenReturn(customer);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected= "{id:103,name:ravi}";
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);


    }

    @Test
    public void getCustomer() throws Exception {

        Long id=103L;

        Customer customer = new Customer(103L,"ravi");
      //  Customer customer= new Customer(id:103,name:"ravi");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/get/customer/id/"+id).accept(
                MediaType.APPLICATION_JSON);

        Mockito.when(customerService.getCustomer(id)).thenReturn(customer);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected= "{id:103,name:ravi}";
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);

    }

    @Test
    public void saveCustomer()  throws Exception {
    Customer customer  = createCustomer();
    ObjectMapper objectMapper = new ObjectMapper();
    String input_json= objectMapper.writeValueAsString(customer);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(                "/save/customer")
                .accept( MediaType.APPLICATION_JSON)
                .content(input_json)
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(customerService.saveCustomer(Mockito.any(customer.getClass()))).thenReturn(customer);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected= "{id:103,name:ravi}";
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);



    }

    @Test
    public void updateCustomer() throws Exception {
        Customer customer  = new Customer(103L,"ajaykumar");
        Customer customer_updated  = new Customer(103L,"ajaykumar");
        ObjectMapper objectMapper = new ObjectMapper();
        String input_json= objectMapper.writeValueAsString(customer);

        String id= customer.getId().toString();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(                "/update/customer/"+id)
                .accept( MediaType.APPLICATION_JSON)
                .content(input_json)
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(customerService.updateCustomer(Mockito.any(customer.getId().getClass()),Mockito.any(customer.getClass()))).thenReturn(customer_updated);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected= "{id:103,name:ajaykumar}";
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);

    }

    @Test
    public void deleteCustomer() throws Exception{

        Long id=103L;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
                "/delete/customer/"+id)
                .accept(MediaType.APPLICATION_JSON);

        Mockito.doNothing().when(customerService).deleteCustomer(id);

         mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    private Customer createCustomer(){
        return new Customer(103L,"ravi");
    }
}