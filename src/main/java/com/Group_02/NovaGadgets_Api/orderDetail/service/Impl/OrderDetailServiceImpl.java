package com.Group_02.NovaGadgets_Api.orderDetail.service.Impl;

import com.Group_02.NovaGadgets_Api.factura.service.FacturaService;
import com.Group_02.NovaGadgets_Api.order.model.OrderEntity;
import com.Group_02.NovaGadgets_Api.order.repository.OrderRepository;
import com.Group_02.NovaGadgets_Api.order.service.OrderService;
import com.Group_02.NovaGadgets_Api.orderDetail.dto.OrdenDetailRequestDTO;
import com.Group_02.NovaGadgets_Api.orderDetail.dto.OrderDetailDTO;
import com.Group_02.NovaGadgets_Api.orderDetail.model.OrderDetailEntity;
import com.Group_02.NovaGadgets_Api.orderDetail.repository.OrderDetailRepository;
import com.Group_02.NovaGadgets_Api.orderDetail.service.OrderDetailService;
import com.Group_02.NovaGadgets_Api.product.model.ProductEntity;
import com.Group_02.NovaGadgets_Api.product.service.ProductService;
import com.Group_02.NovaGadgets_Api.productStore.repository.ProductStoreRepository;
import com.Group_02.NovaGadgets_Api.shared.exception.ResourceNotFoundException;
import com.Group_02.NovaGadgets_Api.shared.exception.ValidationException;
import com.Group_02.NovaGadgets_Api.user.model.UsersEntity;
import com.Group_02.NovaGadgets_Api.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FacturaService facturaService;

    @Autowired
    ProductStoreRepository productStoreRepository;

    public OrderDetailEntity getById(Integer id){
        OrderDetailEntity orderDetailEntity = orderDetailRepository.findById(id).orElse(null);
        if(orderDetailEntity == null){
            throw new ResourceNotFoundException("Order Detail no encontrado");
        }
        return orderDetailEntity;
    }
    @Override
    public void deleteOrderDetail(Integer id) {
        OrderDetailEntity orderDetailEntity = getById(id);
        orderDetailRepository.delete(orderDetailEntity);
    }

    @Override
    public OrderDetailEntity addOrderDetail(Integer orderId, OrderDetailDTO orderDetailDTO) {
        OrderEntity order = orderService.getOrderById(orderId);
        ProductEntity product = productService.GetById(orderDetailDTO.getProductId());

        if(orderDetailRepository.existsByProduct_idAndOrder_id(orderId,orderDetailDTO.getProductId())){
            throw new ValidationException("Order Deatils already exists");
        }

        OrderDetailEntity orderDetailEntity = new OrderDetailEntity(0,orderDetailDTO.getQuantity(),
                order,product);

        orderDetailEntity = orderDetailRepository.save(orderDetailEntity);
        return orderDetailEntity;
    }

    @Override
    public List<OrderDetailDTO> listOrderDetailByOrderId(Integer orderId) {
        OrderEntity order = orderService.getOrderById(orderId);
        List<OrderDetailEntity> list = orderDetailRepository.findByOrder_id(orderId);
        List<OrderDetailDTO> listDTO = new ArrayList<>();
        for (OrderDetailEntity orderDetailEntity: list){
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO(orderDetailEntity.getId(),orderDetailEntity.getQuantity(),
                    orderDetailEntity.getProduct().getId());

            listDTO.add(orderDetailDTO);
        }
        return listDTO;
    }

    @Override
    public OrderDetailEntity updateOrderDetail(Integer orderDetailId, OrderDetailDTO orderDetailDTO) {
        OrderDetailEntity orderDetailEntity = getById(orderDetailId);

        if(orderDetailDTO.getProductId()!=null){
            Integer existingOrderDetailId = orderDetailIdByOrderidAndProductId(orderDetailDTO.getProductId(),
                    orderDetailEntity.getOrder().getId());
            if (existingOrderDetailId != null && !existingOrderDetailId.equals(orderDetailId)) {
                throw new ValidationException("Order Deatils already exists");
            }
            ProductEntity product = productService.GetById(orderDetailDTO.getProductId());
            orderDetailEntity.setProduct(product);
        }

        if(orderDetailDTO.getQuantity()!=null){
            if(orderDetailDTO.getQuantity()<=0){
                throw new ValidationException("Quantity not valid");
            }
            orderDetailEntity.setQuantity(orderDetailDTO.getQuantity());
        }

        return orderDetailRepository.save(orderDetailEntity);
    }

    public Integer orderDetailIdByOrderidAndProductId(Integer product_id, Integer order_id){
        List<OrderDetailEntity> listDuplicados = orderDetailRepository.findByProduct_idAndOrder_id(product_id, order_id);
        if (!listDuplicados.isEmpty()) {
            return listDuplicados.get(0).getId();
        }
        return null;
    }

    public void createOrdenWithOrderDetailByProducts(int userId, List<OrdenDetailRequestDTO> orderDetailDTOList){
        UsersEntity userFound = userRepository.findById(userId).orElse(null);
        if(userFound == null){
            throw new ResourceNotFoundException("User no encontrado");
        }

        OrderEntity order = new OrderEntity(0, java.time.LocalDate.now(),userFound);

        order = orderRepository.save(order);

        double totalInvoiced = 0;

        for(OrdenDetailRequestDTO orderDetailDTO: orderDetailDTOList){
            ProductEntity product = productService.GetById(orderDetailDTO.getProductId());
            OrderDetailEntity orderDetailEntity = new OrderDetailEntity(0,orderDetailDTO.getQuantity(),
                    order,product);
            totalInvoiced += orderDetailDTO.getQuantity()*orderDetailDTO.getProductPrice();
            orderDetailRepository.save(orderDetailEntity);
        }

        facturaService.addFactura(totalInvoiced,order);
    }
}
