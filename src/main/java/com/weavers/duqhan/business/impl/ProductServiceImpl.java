/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.business.impl;

import com.google.gson.Gson;
import com.weavers.duqhan.business.ProductService;
import com.weavers.duqhan.dao.CategoryDao;
import com.weavers.duqhan.dao.ColorDao;
import com.weavers.duqhan.dao.ProductDao;
import com.weavers.duqhan.dao.ProductImgDao;
import com.weavers.duqhan.dao.ProductSizeColorMapDao;
import com.weavers.duqhan.dao.SizeGroupDao;
import com.weavers.duqhan.dao.SizeeDao;
import com.weavers.duqhan.dao.SpecificationDao;
import com.weavers.duqhan.dao.TempProductDao;
import com.weavers.duqhan.dao.TempProductImgDao;
import com.weavers.duqhan.dao.TempProductSizeColorMapDao;
import com.weavers.duqhan.dao.TemtproductlinklistDao;
import com.weavers.duqhan.dao.VendorDao;
import com.weavers.duqhan.domain.Category;
import com.weavers.duqhan.domain.Color;
import com.weavers.duqhan.domain.Product;
import com.weavers.duqhan.domain.ProductImg;
import com.weavers.duqhan.domain.ProductSizeColorMap;
import com.weavers.duqhan.domain.SizeGroup;
import com.weavers.duqhan.domain.Sizee;
import com.weavers.duqhan.domain.Specification;
import com.weavers.duqhan.domain.TempProduct;
import com.weavers.duqhan.domain.TempProductImg;
import com.weavers.duqhan.domain.TempProductSizeColorMap;
import com.weavers.duqhan.domain.Temtproductlinklist;
import com.weavers.duqhan.domain.Vendor;
import com.weavers.duqhan.dto.AddressDto;
import com.weavers.duqhan.dto.AxpProductDto;
import com.weavers.duqhan.dto.CategoryDto;
import com.weavers.duqhan.dto.ColorAndSizeDto;
import com.weavers.duqhan.dto.ColorDto;
import com.weavers.duqhan.dto.ImageDto;
import com.weavers.duqhan.dto.ProductBean;
import com.weavers.duqhan.dto.ProductBeans;
import com.weavers.duqhan.dto.ProductDetailBean;
import com.weavers.duqhan.dto.SizeColorMapDto;
import com.weavers.duqhan.dto.SizeDto;
import com.weavers.duqhan.dto.SkuVal;
import com.weavers.duqhan.dto.SpecificationDto;
import com.weavers.duqhan.dto.StatusBean;
import com.weavers.duqhan.util.CurrencyConverter;
import com.weavers.duqhan.util.FileUploader;
import com.weavers.duqhan.util.MailSender;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Android-3
 */
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;
    @Autowired
    ProductSizeColorMapDao productSizeColorMapDao;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    ProductImgDao productImgDao;
    @Autowired
    SizeeDao sizeeDao;
    @Autowired
    ColorDao colorDao;
    @Autowired
    SizeGroupDao sizeGroupDao;
    @Autowired
    VendorDao vendorDao;
    @Autowired
    SpecificationDao specificationDao;
    @Autowired
    TemtproductlinklistDao temtproductlinklistDao;
    @Autowired
    TempProductDao tempProductDao;
    @Autowired
    TempProductSizeColorMapDao tempProductSizeColorMapDao;
    @Autowired
    TempProductImgDao tempProductImgDao;

    // <editor-fold defaultstate="collapsed" desc="setProductBeans">
    private ProductBeans setProductBeans(List<Product> products, HashMap<Long, ProductSizeColorMap> mapSizeColorMap) {
        ProductBeans productBeans = new ProductBeans();
        List<String> allImages = new ArrayList<>();
        List<ProductBean> beans = new ArrayList<>();
        List<Sizee> sizees = sizeeDao.loadAll();
        HashMap<Long, Sizee> mapSize = new HashMap<>();
        for (Sizee sizee : sizees) {
            mapSize.put(sizee.getId(), sizee);
        }
        List<Color> colors = colorDao.loadAll();
        HashMap<Long, Color> mapColor = new HashMap<>();
        for (Color color : colors) {
            mapColor.put(color.getId(), color);
        }
        List<Category> categorys = categoryDao.loadAll();
        HashMap<Long, String> mapCategory = new HashMap<>();
        for (Category category : categorys) {
            mapCategory.put(category.getId(), category.getName());
        }
        int i = 0;
        for (Product product : products) {
            if (mapSizeColorMap.containsKey(product.getId())) {
                ProductBean bean = new ProductBean();
                bean.setProductId(product.getId());
                bean.setName(product.getName());
                bean.setImgurl(product.getImgurl());
                allImages.add(product.getImgurl());
                bean.setDescription(product.getDescription());
                bean.setCategoryId(product.getCategoryId());
                bean.setCategoryName(mapCategory.get(product.getCategoryId()));
                bean.setSizeId(mapSizeColorMap.get(product.getId()).getSizeId());
                bean.setColorId(mapSizeColorMap.get(product.getId()).getColorId());
                if (mapSizeColorMap.get(product.getId()).getSizeId() != null) {     // if this product have any size
                    bean.setSize(mapSize.get(mapSizeColorMap.get(product.getId()).getSizeId()).getValu());
                }
                if (mapSizeColorMap.get(product.getId()).getColorId() != null) {    // if this product have any color
                    bean.setColor(mapColor.get(mapSizeColorMap.get(product.getId()).getColorId()).getName());
                }
                bean.setPrice(mapSizeColorMap.get(product.getId()).getPrice());
                bean.setDiscountedPrice(mapSizeColorMap.get(product.getId()).getDiscount());
                bean.setDiscountPCT(this.getPercentage(mapSizeColorMap.get(product.getId()).getPrice(), mapSizeColorMap.get(product.getId()).getDiscount()));
                Long qunty = mapSizeColorMap.get(product.getId()).getQuentity();
                bean.setAvailable(qunty.intValue());
                bean.setVendorId(product.getVendorId());
                bean.setShippingRate(product.getShippingRate());
                bean.setShippingTime(product.getShippingTime());
                bean.setExternalLink(product.getExternalLink());
                i = i + qunty.intValue();   // count total product
//            ==========================load specification==================================//
                String specifications = product.getSpecifications();
                bean.setSpecifications(specifications);
                if (specifications != null && !specifications.equals("")) {
                    String[] fiturs = specifications.split(",");
                    HashMap<String, String> map = new HashMap<String, String>();
                    for (String fitur : fiturs) {
                        map.put(fitur.split(":")[0], fitur.split(":")[1]);
                    }
                    bean.setSpecificationsMap(map);
                } else {
                    bean.setSpecificationsMap(new HashMap<String, String>());
                }
                beans.add(bean);
            }
        }
        productBeans.setTotalProducts(i);
        productBeans.setProducts(beans);
        productBeans.setAllImages(allImages);
        return productBeans;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="setTempProductBeans">
    private ProductBeans setTempProductBeans(List<TempProduct> products, HashMap<Long, TempProductSizeColorMap> mapSizeColorMap) {
        ProductBeans productBeans = new ProductBeans();
        List<String> allImages = new ArrayList<>();
        List<ProductBean> beans = new ArrayList<>();
        List<Sizee> sizees = sizeeDao.loadAll();
        HashMap<Long, Sizee> mapSize = new HashMap<>();
        for (Sizee sizee : sizees) {
            mapSize.put(sizee.getId(), sizee);
        }
        List<Color> colors = colorDao.loadAll();
        HashMap<Long, Color> mapColor = new HashMap<>();
        for (Color color : colors) {
            mapColor.put(color.getId(), color);
        }
        List<Category> categorys = categoryDao.loadAll();
        HashMap<Long, String> mapCategory = new HashMap<>();
        for (Category category : categorys) {
            mapCategory.put(category.getId(), category.getName());
        }
        int i = 0;
        for (TempProduct product : products) {
            if (mapSizeColorMap.containsKey(product.getId())) {
                ProductBean bean = new ProductBean();
                bean.setProductId(product.getId());
                bean.setName(product.getName());
                bean.setImgurl(product.getImgurl());
                allImages.add(product.getImgurl());
                bean.setDescription(product.getDescription());
                bean.setCategoryId(product.getCategoryId());
                bean.setCategoryName(mapCategory.get(product.getCategoryId()));
                bean.setSizeId(mapSizeColorMap.get(product.getId()).getSizeId());
                bean.setColorId(mapSizeColorMap.get(product.getId()).getColorId());
                if (mapSizeColorMap.get(product.getId()).getSizeId() != null) {     // if this product have any size
                    bean.setSize(mapSize.get(mapSizeColorMap.get(product.getId()).getSizeId()).getValu());
                }
                if (mapSizeColorMap.get(product.getId()).getColorId() != null) {    // if this product have any color
                    bean.setColor(mapColor.get(mapSizeColorMap.get(product.getId()).getColorId()).getName());
                }
                bean.setPrice(mapSizeColorMap.get(product.getId()).getPrice());
                bean.setDiscountedPrice(mapSizeColorMap.get(product.getId()).getDiscount());
                bean.setDiscountPCT(this.getPercentage(mapSizeColorMap.get(product.getId()).getPrice(), mapSizeColorMap.get(product.getId()).getDiscount()));
                Long qunty = mapSizeColorMap.get(product.getId()).getQuantity();
                bean.setAvailable(qunty.intValue());
                bean.setVendorId(product.getVendorId());
                bean.setShippingRate(product.getShippingRate());
                bean.setShippingTime(product.getShippingTime());
                bean.setExternalLink(product.getExternalLink());
                i = i + qunty.intValue();   // count total product
//            ==========================load specification==================================//
                String specifications = product.getSpecifications();
                bean.setSpecifications(specifications);
                if (specifications != null && !specifications.equals("")) {
                    String[] fiturs = specifications.split(",");
                    HashMap<String, String> map = new HashMap<String, String>();
                    for (String fitur : fiturs) {
                        map.put(fitur.split(":")[0], fitur.split(":")[1]);
                    }
                    bean.setSpecificationsMap(map);
                } else {
                    bean.setSpecificationsMap(new HashMap<String, String>());
                }
                beans.add(bean);
            }
        }
        productBeans.setTotalProducts(i);
        productBeans.setProducts(beans);
        productBeans.setAllImages(allImages);
        return productBeans;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getPercentage">
    private Double getPercentage(Double original, Double discounted) {
        Double discountPct = 0.00;
        Double less = original - discounted;
        if (original != null && discounted != null && less > 0.00) {
            discountPct = (less / original) * 100;  // calculate discount percentage
        }
        DecimalFormat df = new DecimalFormat("#.00");
        discountPct = Double.parseDouble(df.format(discountPct));
        return discountPct;
    }
    // </editor-fold>

    private String saveTempProduct(ProductBean productBean) {
        String status = "ERROR: Product can not be saved!!";
        if (productBean != null) {
            Category parentCategory = categoryDao.loadById(productBean.getCategoryId());//>>>>>>>>>>>>>>
            TempProduct product = new TempProduct();
            product.setId(null);
            product.setName(productBean.getName());//>>>>>>>>>>>>>>>>>
            product.setImgurl(productBean.getImgurl());//>>>>>>>>>>>>>>>>
            product.setCategoryId(productBean.getCategoryId());//>>>>>>>>>>>
            product.setDescription(productBean.getDescription());//>>>>>>>>>>>>>>>>
            product.setLastUpdate(new Date());
            product.setVendorId(productBean.getVendorId());     //>>>>>>>>>>>>>>>>>
            product.setParentPath(parentCategory.getParentPath());
            product.setExternalLink(productBean.getExternalLink()); //>>>>>>>>>>>>>>
            product.setSpecifications(productBean.getSpecifications());//>>>>>>>>>>>>
            product.setShippingTime(productBean.getShippingTime());
            product.setShippingRate(productBean.getShippingRate());
            TempProduct product1 = tempProductDao.save(product);
            if (product1 != null) {
                //====multiple ProductSizeColorMap added=======//
                List<SizeColorMapDto> sizeColorMapDtos = productBean.getSizeColorMaps();//>>>>>>>>>>>>>>
                if (!sizeColorMapDtos.isEmpty()) {
                    for (SizeColorMapDto sizeColorMapDto : sizeColorMapDtos) {
                        TempProductSizeColorMap sizeColorMap = new TempProductSizeColorMap();
                        sizeColorMap.setId(null);
                        sizeColorMap.setColorId(sizeColorMapDto.getColorId());
                        sizeColorMap.setSizeId(sizeColorMapDto.getSizeId());
                        sizeColorMap.setDiscount(sizeColorMapDto.getSalesPrice());
                        sizeColorMap.setPrice(sizeColorMapDto.getOrginalPrice());
                        sizeColorMap.setQuantity(sizeColorMapDto.getCount());
                        sizeColorMap.setProductId(product1.getId());
                        sizeColorMap.setProductHeight(sizeColorMapDto.getProductHeight());
                        sizeColorMap.setProductLength(sizeColorMapDto.getProductLength());
                        sizeColorMap.setProductWeight(sizeColorMapDto.getProductWeight());
                        sizeColorMap.setProductWidth(sizeColorMapDto.getProductWidth());
                        TempProductSizeColorMap sizeColorMap1 = tempProductSizeColorMapDao.save(sizeColorMap);
                    }
                }

                //===========multiple image add==========//
                List<ImageDto> imageDtos = productBean.getImageDtos();//>>>>>>>>>>>>
                if (!imageDtos.isEmpty()) {
                    for (ImageDto imageDto : imageDtos) {
                        TempProductImg productImg = new TempProductImg();
                        productImg.setId(null);
                        productImg.setImgUrl(imageDto.getImgUrl());
                        productImg.setProductId(product1.getId());
                        tempProductImgDao.save(productImg);
                    }
                }
                status = "success";
            }
        }
        return status;
    }

    /*@Override
    public ColorAndSizeDto getColorSizeList() { // get color size category from database for add produc page on load.
        List<Sizee> sizees = sizeeDao.loadAll();
        List<Color> colors = colorDao.loadAll();
        List<Category> categorys = categoryDao.loadAll();
        List<SizeGroup> sizeGroups = sizeGroupDao.loadAll();
        List<Vendor> vendors = vendorDao.loadAll();

        ColorAndSizeDto colorAndSizeDto = new ColorAndSizeDto();
        List<SizeDto> sizeDtos = new ArrayList<>();
        List<ColorDto> colorDtos = new ArrayList<>();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        List<SizeDto> sizeGroupDtos = new ArrayList<>();
        List<AddressDto> vendorsDtos = new ArrayList<>();
        for (Sizee sizee : sizees) {    //=============get size.
            SizeDto SizeDto = new SizeDto();
            SizeDto.setSizeId(sizee.getId());
            SizeDto.setSizeText(sizee.getValu());
            sizeDtos.add(SizeDto);
        }
        for (Color color : colors) {    //=============get colors.
            ColorDto ColorDto = new ColorDto();
            ColorDto.setColorId(color.getId());
            ColorDto.setColorText(color.getName());
            colorDtos.add(ColorDto);
        }
        for (Category category : categorys) {   //=============get category.
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setCategoryId(category.getId());
            categoryDto.setCategoryName(category.getName());
            categoryDtos.add(categoryDto);
        }
        for (SizeGroup sizeGroup : sizeGroups) {    //=============get size groups.
            SizeDto sizeGroupDto = new SizeDto();
            sizeGroupDto.setSizeGroupId(sizeGroup.getId());
            sizeGroupDto.setSizeText(sizeGroup.getName());
            sizeGroupDtos.add(sizeGroupDto);
        }
        for (Vendor vendor : vendors) {    //=============get Vendor.
            AddressDto vendorDto = new AddressDto();
            vendorDto.setUserId(vendor.getId());
            vendorDto.setContactName(vendor.getVendorName());
            vendorsDtos.add(vendorDto);
        }
        colorAndSizeDto.setSizeGroupDtos(sizeGroupDtos);
        colorAndSizeDto.setSizeDtos(sizeDtos);
        colorAndSizeDto.setColorDtos(colorDtos);
        colorAndSizeDto.setCategoryDtos(categoryDtos);
        colorAndSizeDto.setVendorDtos(vendorsDtos);
        return colorAndSizeDto;
    }*/
    @Override
    public ColorAndSizeDto getCategories() {
        List<Category> categorys = categoryDao.loadAll();

        ColorAndSizeDto colorAndSizeDto = new ColorAndSizeDto();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categorys) {   //=============get category.
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setCategoryId(category.getId());
            categoryDto.setCategoryName(category.getName());
            categoryDtos.add(categoryDto);
        }
        colorAndSizeDto.setCategoryDtos(categoryDtos);
        return colorAndSizeDto;
    }

    @Override
    public ColorAndSizeDto getSizes() {
        List<Sizee> sizees = sizeeDao.loadAll();

        ColorAndSizeDto colorAndSizeDto = new ColorAndSizeDto();
        List<SizeDto> sizeDtos = new ArrayList<>();
        for (Sizee sizee : sizees) {    //=============get size.
            SizeDto SizeDto = new SizeDto();
            SizeDto.setSizeId(sizee.getId());
            SizeDto.setSizeText(sizee.getValu());
            sizeDtos.add(SizeDto);
        }
        colorAndSizeDto.setSizeDtos(sizeDtos);
        return colorAndSizeDto;
    }

    @Override
    public ColorAndSizeDto getSizeGroupe() {
        List<SizeGroup> sizeGroups = sizeGroupDao.loadAll();

        ColorAndSizeDto colorAndSizeDto = new ColorAndSizeDto();
        List<SizeDto> sizeGroupDtos = new ArrayList<>();
        for (SizeGroup sizeGroup : sizeGroups) {    //=============get size groups.
            SizeDto sizeGroupDto = new SizeDto();
            sizeGroupDto.setSizeGroupId(sizeGroup.getId());
            sizeGroupDto.setSizeText(sizeGroup.getName());
            sizeGroupDtos.add(sizeGroupDto);
        }
        colorAndSizeDto.setSizeGroupDtos(sizeGroupDtos);
        return colorAndSizeDto;
    }

    @Override
    public ColorAndSizeDto getColors() {
        List<Color> colors = colorDao.loadAll();

        ColorAndSizeDto colorAndSizeDto = new ColorAndSizeDto();
        List<ColorDto> colorDtos = new ArrayList<>();
        for (Color color : colors) {    //=============get colors.
            ColorDto ColorDto = new ColorDto();
            ColorDto.setColorId(color.getId());
            ColorDto.setColorText(color.getName());
            colorDtos.add(ColorDto);
        }
        colorAndSizeDto.setColorDtos(colorDtos);
        return colorAndSizeDto;
    }

    @Override
    public ColorAndSizeDto getVendors() {
        List<Vendor> vendors = vendorDao.loadAll();

        ColorAndSizeDto colorAndSizeDto = new ColorAndSizeDto();
        List<AddressDto> vendorsDtos = new ArrayList<>();
        for (Vendor vendor : vendors) {    //=============get Vendor.
            AddressDto vendorDto = new AddressDto();
            vendorDto.setUserId(vendor.getId());
            vendorDto.setContactName(vendor.getVendorName());
            vendorsDtos.add(vendorDto);
        }
        colorAndSizeDto.setVendorDtos(vendorsDtos);
        return colorAndSizeDto;
    }

    @Override
    public ColorAndSizeDto getSpecificationsByCategoryId(Long categoryId) {
        List<Specification> specifications = specificationDao.getSpecificationsByCategoryId(categoryId);
        List<SpecificationDto> specificationDtos = new ArrayList<>();
        for (Specification specification : specifications) {
            SpecificationDto specificationDto = new SpecificationDto();
            String[] values = specification.getFeaturesValue().split(",");
            specificationDto.setId(specification.getId());
            specificationDto.setName(specification.getFeatures());
            specificationDto.setValues(Arrays.asList(values));
            specificationDtos.add(specificationDto);
        }
        ColorAndSizeDto colorAndSizeDto = new ColorAndSizeDto();
        colorAndSizeDto.setSpecifications(specificationDtos);
        return colorAndSizeDto;
    }

    /*@Override
    public ProductBeans getProductsByCategory(Long categoryId, int start, int limit) {
        List<Product> products = productDao.getProductsByCategoryIncludeChild(categoryId, start, limit);  // Find category wise product 
        List<Long> productIds = new ArrayList<>();
        for (Product product : products) {
            productIds.add(product.getId());
        }
        HashMap<Long, ProductSizeColorMap> mapSizeColorMaps = productSizeColorMapDao.getSizeColorMapbyMinPriceIfAvailable(productIds);
        ProductBeans productBeans = this.setProductBeans(products, mapSizeColorMaps);
        productBeans.setCategoryName(categoryDao.loadById(categoryId).getName());
        return productBeans;
    }

    @Override
    public ProductBeans getProductsByRecentView(Long userId, int start, int limit) {
        List<Product> products = productDao.getAllRecentViewProduct(userId, start, limit);    // Find recent view product 
        List<Long> productIds = new ArrayList<>();
        for (Product product : products) {
            productIds.add(product.getId());
        }
        HashMap<Long, ProductSizeColorMap> mapSizeColorMaps = productSizeColorMapDao.getSizeColorMapbyMinPriceRecentView(productIds);
        return this.setProductBeans(products, mapSizeColorMaps);
    }

    @Override
    public ProductBeans getAllProducts(int start, int limit) {
        List<Product> products = productDao.getAllAvailableProduct(start, limit);   // Find all products 
        List<Long> productIds = new ArrayList<>();
        for (Product product : products) {
            productIds.add(product.getId());
        }
        HashMap<Long, ProductSizeColorMap> mapSizeColorMaps = productSizeColorMapDao.getSizeColorMapbyMinPriceIfAvailable(productIds);
        return this.setProductBeans(products, mapSizeColorMaps);
    }*/
    @Override
    public ProductBeans getAllProductsIncloudeZeroAvailable(int start, int limit) {
        List<Product> products = productDao.getAllAvailableProduct(start, limit);   // Find all products 
        List<Long> productIds = new ArrayList<>();
        for (Product product : products) {
            productIds.add(product.getId());
        }
        HashMap<Long, ProductSizeColorMap> mapSizeColorMaps = productSizeColorMapDao.getSizeColorMapByProductIds(productIds);
        return this.setProductBeans(products, mapSizeColorMaps);
    }

    /*@Override
    public ProductBeans searchProducts(ProductRequistBean requistBean) {
        List<Product> products = productDao.SearchProductByNameAndDescription(requistBean.getName(), requistBean.getStart(), requistBean.getLimit());   // Search products by name and Description
        List<Long> productIds = new ArrayList<>();
        for (Product product : products) {
            productIds.add(product.getId());
        }
        HashMap<Long, ProductSizeColorMap> mapSizeColorMaps = productSizeColorMapDao.getSizeColorMapbyMinPriceIfAvailable(productIds);
        ProductBeans productBeans = this.setProductBeans(products, mapSizeColorMaps);
        productBeans.setSearchString(requistBean.getName());
        return productBeans;
    }

    @Override
    public ProductDetailBean getProductDetailsById(Long productId, Long userId) {
        ProductDetailBean productDetailBean = new ProductDetailBean();
        Product product = productDao.loadById(productId);
        if (product != null) {
            Category category = categoryDao.loadById(product.getCategoryId());
            List<ProductImg> imgs = productImgDao.getProductImgsByProductId(productId);
            List<ProductSizeColorMap> sizeColorMaps = productSizeColorMapDao.getSizeColorMapByProductId(productId);
            List<Sizee> sizees = sizeeDao.loadAll();
            HashMap<Long, Sizee> mapSize = new HashMap<>();
            for (Sizee sizee : sizees) {
                mapSize.put(sizee.getId(), sizee);
            }
            List<Color> colors = colorDao.loadAll();
            HashMap<Long, Color> mapColor = new HashMap<>();
            for (Color color : colors) {
                mapColor.put(color.getId(), color);
            }
            productDetailBean.setProductId(product.getId());
            productDetailBean.setName(product.getName());
            productDetailBean.setDescription(product.getDescription());
            productDetailBean.setCategoryId(category.getId());
            productDetailBean.setCategoryName(category.getName());
            productDetailBean.setProductImg(product.getImgurl());
            productDetailBean.setVendorId(product.getVendorId());
            productDetailBean.setShippingCost(product.getShippingRate());
            productDetailBean.setShippingTime(product.getShippingTime());
            //===============================add imgDto==============================
            List<ImageDto> imgDtos = new ArrayList<>();
            ImageDto imgDto;
            imgDto = new ImageDto();
            imgDto.setImgUrl(product.getImgurl());
            imgDtos.add(imgDto);    //including front image
            for (ProductImg productImg : imgs) {
                imgDto = new ImageDto();
                imgDto.setImgUrl(productImg.getImgUrl());
                imgDtos.add(imgDto);
            }
            productDetailBean.setImages(imgDtos);
            //====================================add SizeDto========================
            Set<SizeDto> sizeDtos = new HashSet<>();
            Set<SizeDto> sizeDtos2 = new HashSet<>();
            Set<ColorDto> colorDtos = new HashSet<>();
            HashMap<Long, Object> check = new HashMap<>();
            HashMap<Long, Object> check1 = new HashMap<>();
            HashMap<Long, List<SizeColorMapDto>> mapSizeColorMapDto = new HashMap<>();
            Double orginalPrice = 0.0;
            Double salesPrice = 0.0;
            int flg = 0;
            for (ProductSizeColorMap sizeColorMap : sizeColorMaps) {
                SizeDto sizeDto = new SizeDto();
                if (mapSize.get(sizeColorMap.getSizeId()) != null) {
                    sizeDto.setSizeId(sizeColorMap.getSizeId());
                    sizeDto.setSizeText(mapSize.get(sizeColorMap.getSizeId()).getValu());
                }
                if (!check.containsKey(sizeColorMap.getSizeId())) {
                    sizeDtos.add(sizeDto);
                    check.put(sizeColorMap.getSizeId(), null);
                }

                if (mapSizeColorMapDto.containsKey(sizeColorMap.getSizeId())) {
                    SizeColorMapDto sizeColorMapDto = new SizeColorMapDto();
                    if (mapColor.get(sizeColorMap.getColorId()) != null) {
                        sizeColorMapDto.setColorId(sizeColorMap.getColorId());
                        sizeColorMapDto.setColorText(mapColor.get(sizeColorMap.getColorId()).getName());
                    }
                    sizeColorMapDto.setMapId(sizeColorMap.getId());
                    sizeColorMapDto.setOrginalPrice(sizeColorMap.getPrice());
                    if (sizeColorMap.getPrice() < orginalPrice) {
                        orginalPrice = sizeColorMap.getPrice();
                        salesPrice = sizeColorMap.getDiscount();
                    }
                    sizeColorMapDto.setSalesPrice(sizeColorMap.getDiscount());
                    sizeColorMapDto.setDiscount(this.getPercentage(sizeColorMap.getPrice(), sizeColorMap.getDiscount()));
                    sizeColorMapDto.setCount(sizeColorMap.getQuentity());
                    mapSizeColorMapDto.get(sizeColorMap.getSizeId()).add(sizeColorMapDto);
                } else {
                    if (flg == 0) {
                        orginalPrice = sizeColorMap.getPrice();
                        salesPrice = sizeColorMap.getDiscount();
                    }
                    flg++;
                    List<SizeColorMapDto> sizeColorMapDtos = new ArrayList<>();
                    SizeColorMapDto sizeColorMapDto = new SizeColorMapDto();
                    if (mapColor.get(sizeColorMap.getColorId()) != null) {
                        sizeColorMapDto.setColorId(sizeColorMap.getColorId());
                        sizeColorMapDto.setColorText(mapColor.get(sizeColorMap.getColorId()).getName());
                    }
                    sizeColorMapDto.setMapId(sizeColorMap.getId());
                    sizeColorMapDto.setOrginalPrice(sizeColorMap.getPrice());
                    if (sizeColorMap.getPrice() < orginalPrice) {
                        orginalPrice = sizeColorMap.getPrice();
                        salesPrice = sizeColorMap.getDiscount();
                    }
                    sizeColorMapDto.setSalesPrice(sizeColorMap.getDiscount());
                    sizeColorMapDto.setDiscount(this.getPercentage(sizeColorMap.getPrice(), sizeColorMap.getDiscount()));
                    sizeColorMapDto.setCount(sizeColorMap.getQuentity());
                    sizeColorMapDtos.add(sizeColorMapDto);
                    mapSizeColorMapDto.put(sizeColorMap.getSizeId(), sizeColorMapDtos);
                }

                ColorDto colorDto = new ColorDto();
                if (mapColor.get(sizeColorMap.getColorId()) != null) {
                    colorDto.setColorId(sizeColorMap.getColorId());
                    colorDto.setColorText(mapColor.get(sizeColorMap.getColorId()).getName());
                }
                if (!check1.containsKey(sizeColorMap.getColorId())) {
                    colorDtos.add(colorDto);
                    check1.put(sizeColorMap.getColorId(), null);
                }
            }
            for (SizeDto sizeDto1 : sizeDtos) {
                SizeDto sizeDto2 = new SizeDto();
                sizeDto2.setSizeId(sizeDto1.getSizeId());
                sizeDto2.setSizeText(sizeDto1.getSizeText());
                sizeDto2.setSizeColorMap(mapSizeColorMapDto.get(sizeDto1.getSizeId()));
                sizeDtos2.add(sizeDto2);
            }
//            ==========================load specification==================================//
            String specifications = product.getSpecifications();
            if (specifications != null && !specifications.equals("")) {
                String[] fiturs = specifications.split(",");
                HashMap<String, String> map = new HashMap<String, String>();
                for (String fitur : fiturs) {
                    map.put(fitur.split(":")[0], fitur.split(":")[1]);
                }
                productDetailBean.setSpecifications(map);

            } else {
                productDetailBean.setSpecifications(new HashMap<String, String>());
            }

            productDetailBean.setSizes(sizeDtos2);
            productDetailBean.setColors(colorDtos);
            productDetailBean.setOrginalPrice(orginalPrice);
            productDetailBean.setSalesPrice(salesPrice);
            productDetailBean.setDiscount(this.getPercentage(orginalPrice, salesPrice));
            productDetailBean.setArrival("Not set yet..");
            productDetailBean.setShippingCost(null);
            productDetailBean.setRelatedProducts(new ProductBeans());
            //==========================Save in recent view table===========================//
            if (userId != null) {
                RecentView recentView = new RecentView();
                recentView.setId(null);
                recentView.setProductId(product.getId());
                recentView.setUserId(userId);
                recentView.setViewDate(new Date());
                recentViewDao.save(recentView);
            }
        } else {
            productDetailBean.setStatus("No Product Found");
        }
        return productDetailBean;
    }

    @Override
    public String addProductToCart(ProductRequistBean requistBean) {
        String status = "failure";
        Cart cart2 = cartDao.loadByUserIdAndMapId(requistBean.getUserId(), requistBean.getMapId());
        if (cart2 != null) {
            status = "Product already added";
        } else {
            Cart cart = new Cart();
            cart.setId(null);
            cart.setLoadDate(new Date());
            cart.setSizecolormapId(requistBean.getMapId());
            cart.setUserId(requistBean.getUserId());
            Cart cart1 = cartDao.save(cart);    // add product to cart.
            if (cart1 != null) {
                status = "success";
            }
        }
        return status;
    }

    @Override
    public String removeProductFromCart(ProductRequistBean requistBean) {
        String status = "failure";
        Cart cart = cartDao.getCartByIdAndMapId(requistBean.getCartId(), requistBean.getMapId());
        if (cart != null) {
            cartDao.delete(cart);   //remove product from cart.
            status = "success";
        }
        return status;
    }

    @Override
    public CartBean getCartForUser(Long userId) {
        CartBean cartBean = new CartBean();
        HashMap<Long, Cart> MapCart = new HashMap<>();
        List<ProductSizeColorMap> sizeColorMaps = cartDao.getProductSizeColorMapByUserId(userId);   // Load user cart
        if (!sizeColorMaps.isEmpty()) {
            List<Cart> carts = cartDao.getCartByUserId(userId);
            for (Cart cart : carts) {
                MapCart.put(cart.getSizecolormapId(), cart);
            }
            List<Long> productIds = new ArrayList<>();
            List<Long> colorIds = new ArrayList<>();
            List<Long> sizeIds = new ArrayList<>();
            for (ProductSizeColorMap sizeColorMap : sizeColorMaps) {
                productIds.add(sizeColorMap.getProductId());
                colorIds.add(sizeColorMap.getColorId());
                sizeIds.add(sizeColorMap.getSizeId());
            }
            List<Product> products = productDao.loadByIds(productIds);
            HashMap<Long, Product> MapProduct = new HashMap<>();
            for (Product product : products) {
                MapProduct.put(product.getId(), product);
            }
            List<Color> colors = colorDao.loadByIds(colorIds);
            HashMap<Long, Color> MapColor = new HashMap<>();
            for (Color color : colors) {
                MapColor.put(color.getId(), color);
            }
            List<Sizee> sizees = sizeeDao.loadByIds(sizeIds);
            HashMap<Long, Sizee> MapSizee = new HashMap<>();
            for (Sizee sizee : sizees) {
                MapSizee.put(sizee.getId(), sizee);
            }
            //============================================================//
            List<ProductBean> productBeans = new ArrayList<>();
            double itemTotal = 0.0;         //2000
            double orderTotal = 0.0;        //1500
            double discountTotal = 0.0;     //500
            double discountPctTotal = 0.0;  //25
            //=====================ready product bean====================//
            for (ProductSizeColorMap sizeColorMap : sizeColorMaps) {
                ProductBean productBean = new ProductBean();
                productBean.setQty("1");
                productBean.setSizeColorMapId(sizeColorMap.getId());
                productBean.setProductId(sizeColorMap.getProductId());
                productBean.setName(MapProduct.get(sizeColorMap.getProductId()).getName());
                productBean.setDescription(MapProduct.get(sizeColorMap.getProductId()).getDescription());
                if (MapSizee.get(sizeColorMap.getSizeId()) != null) {
                    productBean.setSize(MapSizee.get(sizeColorMap.getSizeId()).getValu());
                }
                if (MapColor.get(sizeColorMap.getColorId()) != null) {
                    productBean.setColor(MapColor.get(sizeColorMap.getColorId()).getName());
                }
//                productBean.setImgurl(MapProductImg.get(sizeColorMap.getProductImgId()).getImgUrl());
                productBean.setImgurl(MapProduct.get(sizeColorMap.getProductId()).getImgurl());
                productBean.setPrice(sizeColorMap.getPrice());
                itemTotal = itemTotal + sizeColorMap.getPrice();
                productBean.setDiscountedPrice(sizeColorMap.getDiscount());
                orderTotal = orderTotal + sizeColorMap.getDiscount();
                productBean.setDiscountPCT(this.getPercentage(sizeColorMap.getPrice(), sizeColorMap.getDiscount()));
                productBean.setAvailable(Long.valueOf(sizeColorMap.getQuentity()).intValue());
                productBean.setCartId(MapCart.get(sizeColorMap.getId()).getId());
                if (StatusConstants.IS_SHIPMENT) {
                    productBean.setShippingRate(MapProduct.get(sizeColorMap.getProductId()).getShippingRate());
                    productBean.setShippingTime(MapProduct.get(sizeColorMap.getProductId()).getShippingTime());
                } else {
                    productBean.setShippingRate(0.0);
                    productBean.setShippingTime("21");
                }
                productBean.setVendorId(MapProduct.get(sizeColorMap.getProductId()).getVendorId());
                productBean.setProductHeight(sizeColorMap.getProductHeight());
                productBean.setProductLength(sizeColorMap.getProductLength());
                productBean.setProductWeight(sizeColorMap.getProductWeight());
                productBean.setProductWidth(sizeColorMap.getProductWidth());
                productBeans.add(productBean);
            }
            discountTotal = itemTotal - orderTotal;
            discountPctTotal = this.getPercentage(itemTotal, orderTotal);   // calcute discount percentage.
            cartBean.setProducts(productBeans);
            cartBean.setItemTotal(itemTotal);
            cartBean.setOrderTotal(orderTotal);
            cartBean.setDiscountTotal(discountTotal);
            cartBean.setDiscountPctTotal(discountPctTotal);
        } else {
            cartBean.setStatus("Cart is empty");
        }
        return cartBean;
    }*/
    @Override
    public String saveProduct(ProductBean productBean) {
        String status = "ERROR: Product can not be saved!!";
        if (productBean != null) {
            Category parentCategory = categoryDao.loadById(productBean.getCategoryId());//>>>>>>>>>>>>>>
            Product product = new Product();
            product.setId(null);
            product.setName(productBean.getName());//>>>>>>>>>>>>>>>>>
            product.setImgurl(productBean.getImgurl());//>>>>>>>>>>>>>>>>
            product.setCategoryId(productBean.getCategoryId());//>>>>>>>>>>>
            product.setDescription(productBean.getDescription());//>>>>>>>>>>>>>>>>
            product.setLastUpdate(new Date());
            product.setVendorId(productBean.getVendorId());     //>>>>>>>>>>>>>>>>>
            product.setParentPath(parentCategory.getParentPath());
            product.setExternalLink(productBean.getExternalLink()); //>>>>>>>>>>>>>>
            product.setSpecifications(productBean.getSpecifications());//>>>>>>>>>>>>
            product.setShippingTime(productBean.getShippingTime());
            product.setShippingRate(productBean.getShippingRate());
            Product product1 = productDao.save(product);
            if (product1 != null) {
                //====multiple ProductSizeColorMap added=======//
                List<SizeColorMapDto> sizeColorMapDtos = productBean.getSizeColorMaps();//>>>>>>>>>>>>>>
                if (!sizeColorMapDtos.isEmpty()) {
                    for (SizeColorMapDto sizeColorMapDto : sizeColorMapDtos) {
                        ProductSizeColorMap sizeColorMap = new ProductSizeColorMap();
                        sizeColorMap.setId(null);
                        sizeColorMap.setColorId(sizeColorMapDto.getColorId());
                        sizeColorMap.setSizeId(sizeColorMapDto.getSizeId());
                        sizeColorMap.setDiscount(sizeColorMapDto.getSalesPrice());
                        sizeColorMap.setPrice(sizeColorMapDto.getOrginalPrice());
                        sizeColorMap.setQuentity(sizeColorMapDto.getCount());
                        sizeColorMap.setProductId(product1.getId());
                        sizeColorMap.setProductHeight(sizeColorMapDto.getProductHeight());
                        sizeColorMap.setProductLength(sizeColorMapDto.getProductLength());
                        sizeColorMap.setProductWeight(sizeColorMapDto.getProductWeight());
                        sizeColorMap.setProductWidth(sizeColorMapDto.getProductWidth());
                        ProductSizeColorMap sizeColorMap1 = productSizeColorMapDao.save(sizeColorMap);
                    }
                }

                //===========multiple image add==========//
                List<ImageDto> imageDtos = productBean.getImageDtos();//>>>>>>>>>>>>
                if (!imageDtos.isEmpty()) {
                    for (ImageDto imageDto : imageDtos) {
                        ProductImg productImg = new ProductImg();
                        productImg.setId(null);
                        productImg.setImgUrl(imageDto.getImgUrl());
                        productImg.setProductId(product1.getId());
                        productImgDao.save(productImg);
                    }
                }
                status = "Product saved.";
            }
        }
        return status;
    }

    @Override
    public String saveCategory(CategoryDto categoryDto) {
        String status = "ERROR: Category can not be saved!!";
        Category parentCategory = categoryDao.loadById(categoryDto.getPatentId());
        if (categoryDto.getCategoryName() != null && null != parentCategory) {
            Category category = new Category();
            category.setId(null);
            category.setName(categoryDto.getCategoryName());
            category.setParentId(categoryDto.getPatentId());
            category.setParentPath(parentCategory.getParentPath() + categoryDto.getPatentId() + "=");
            Category category2 = categoryDao.save(category);    //save new category
            if (category2 != null) {
                status = "Category saved.";
            }
        }
        return status;
    }

    @Override
    public String saveSize(SizeDto sizeDto) {
        Sizee sizee = new Sizee();
        sizee.setId(null);
        sizee.setGroupId(sizeDto.getSizeGroupId());
        sizee.setValu(sizeDto.getSizeText());
        sizee.setUnit(sizeDto.getSizeText());
        Sizee sizee1 = sizeeDao.save(sizee);    //save new size
        if (sizee1 == null) {
            return "ERROR: Size can not be saved!!";
        } else {
            return "Size saved..";
        }
    }

    @Override
    public String saveSizeGroup(String sizeGroupName) {
        SizeGroup sizeGroup = new SizeGroup();
        sizeGroup.setId(null);
        sizeGroup.setName(sizeGroupName);
        SizeGroup sizeGroup1 = sizeGroupDao.save(sizeGroup);    //save new size group
        if (sizeGroup1 == null) {
            return "ERROR: SizeGroup can not be saved!!";
        } else {
            return "SizeGroup saved..";
        }
    }

    @Override
    public String saveColor(String color) {
        Color color1 = new Color();
        color1.setId(null);
        color1.setName(color);
        color1.setCode(color);
        Color color2 = colorDao.save(color1);   //save new color
        if (color2 == null) {
            return "ERROR: Color can not be saved!!";
        } else {
            return "Color saved..";
        }
    }

    @Override
    public String saveSpecification(SpecificationDto specificationDto) {
        Specification specification = new Specification();
        specification.setId(null);
        specification.setCategoryId(specificationDto.getId());
        specification.setFeatures(specificationDto.getName());
        specification.setFeaturesValue(specificationDto.getValue() + ",");
        Specification specification2 = specificationDao.save(specification);   //save new specification
        if (specification2 == null) {
            return "ERROR: Specification can not be saved!!";
        } else {
            return "Color saved..";
        }
    }

    @Override
    public String saveSpecificationValue(SpecificationDto specificationDto) {
        Specification specification = specificationDao.loadById(specificationDto.getId());
        String featureValue = specification.getFeaturesValue();
        if (featureValue != null) {
            featureValue = featureValue + specificationDto.getValue() + ",";
        } else {
            featureValue = specificationDto.getValue() + ",";
        }
        specification.setFeaturesValue(featureValue);
        Specification specification2 = specificationDao.save(specification);   //save new specification value
        if (specification2 == null) {
            return "ERROR: Specification value can not be saved!!";
        } else {
            return "Color saved..";
        }
    }

    /* @Override
    public Long getCartCountFoAUser(Long userId) {
        return cartDao.getCartCountByUserId(userId);    //number of item in cart fo a user.
    }*/
    @Override
    public String saveProductImage(ProductBean productBean) {
        return FileUploader.uploadImage(productBean.getFrontImage()).getUrl();
    }

    /*@Override
    public CategorysBean getChildById(Long parentId) {
        CategorysBean categorysBean = new CategorysBean();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        List<Category> categorys = categoryDao.getChildByParentId(parentId);
        if (categorys.isEmpty()) {
            categorysBean.setStatus("No child category found");
            categorysBean.setStatusCode("403");
        } else {
            for (Category category : categorys) {
                CategoryDto categoryDto = new CategoryDto();
                categoryDto.setCategoryId(category.getId());
                categoryDto.setCategoryName(category.getName());
                categoryDtos.add(categoryDto);
            }
            categorysBean.setCategoryDtos(categoryDtos);
            categorysBean.setChildCount(categoryDtos.size());
            List<Product> products = productDao.getProductsByCategory(parentId);
            categorysBean.setProductCount(0);
            if (!products.isEmpty()) {
                categorysBean.setProductCount(products.size());
            }
            categorysBean.setStatus("success");
            categorysBean.setStatusCode("200");
        }
        return categorysBean;
    }

    @Override
    public OrderDetailsBean getOrderDetails(Long userId, int start, int limit) {
        OrderDetailsBean orderDetailsBean = new OrderDetailsBean();
        List<OrderDetailsDto> orderdetailsDtos = new ArrayList<>();
        List<Object[]> objects = orderDetailsDao.getDetailByUserId(userId, start, limit);
        if (!objects.isEmpty()) {
            Object[] od = objects.get(0);
            OrderDetails details = (OrderDetails) od[0];
            PaymentDetail paymentDetail = paymentDetailDao.getDetailByPayKey(details.getPaymentKey());
            List<UserAddress> userAddresses = userAddressDao.getAddressByUserId(userId);
            HashMap<Long, UserAddress> mapUserAddress = new HashMap<>();
            for (UserAddress userAddress : userAddresses) {
                mapUserAddress.put(userAddress.getId(), userAddress);
            }
            List<Product> products = productDao.loadAll();
            HashMap<Long, Product> mapProduct = new HashMap<>();
            for (Product product : products) {
                mapProduct.put(product.getId(), product);
            }
            List<Sizee> sizees = sizeeDao.loadAll();
            HashMap<Long, Sizee> mapSize = new HashMap<>();
            for (Sizee sizee : sizees) {
                mapSize.put(sizee.getId(), sizee);
            }
            List<Color> colors = colorDao.loadAll();
            HashMap<Long, Color> mapColor = new HashMap<>();
            for (Color color : colors) {
                mapColor.put(color.getId(), color);
            }
            for (Object[] object : objects) {
                OrderDetails orderDetailse = (OrderDetails) object[0];//********************
                ProductSizeColorMap sizeColorMap = (ProductSizeColorMap) object[1];//***********
                OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
                orderDetailsDto.setAddressDto(this.setAddressDto(mapUserAddress.get(orderDetailse.getAddressId())));
                if (mapColor.containsKey(sizeColorMap.getColorId())) {
                    orderDetailsDto.setColor(mapColor.get(sizeColorMap.getColorId()).getName());
                }
                if (mapSize.containsKey(sizeColorMap.getSizeId())) {
                    orderDetailsDto.setSize(mapSize.get(sizeColorMap.getSizeId()).getValu());
                }
                orderDetailsDto.setOrderId(orderDetailse.getOrderId());
                orderDetailsDto.setDeliveryDate(DateFormater.formate(orderDetailse.getDeliveryDate()));
                orderDetailsDto.setEmail(mapUserAddress.get(orderDetailse.getAddressId()).getEmail());
                orderDetailsDto.setMapId(orderDetailse.getMapId());
                orderDetailsDto.setOrderDate(DateFormater.formate(orderDetailse.getOrderDate()));
                orderDetailsDto.setPaymentAmount(orderDetailse.getPaymentAmount());
                orderDetailsDto.setPaymentKey(orderDetailse.getPaymentKey());
                orderDetailsDto.setPhone(mapUserAddress.get(orderDetailse.getAddressId()).getPhone());
                orderDetailsDto.setProductName(mapProduct.get(sizeColorMap.getProductId()).getName());
                orderDetailsDto.setStatus(orderDetailse.getStatus());
                orderDetailsDto.setProdImg(mapProduct.get(sizeColorMap.getProductId()).getImgurl());
                orderDetailsDto.setPrice(sizeColorMap.getPrice());
                orderDetailsDto.setDiscount(this.getPercentage(sizeColorMap.getPrice(), orderDetailse.getPaymentAmount()));
                orderDetailsDto.setQuty(orderDetailse.getQuentity());
                orderDetailsDto.setReturnStatus(orderDetailse.getReturnStatus());
                ShipmentTable shipmentTable = orderDetailse.getShipmentId() != null ? shippingService.getShipmentTableByShipmentId(orderDetailse.getShipmentId()) : null;
                orderDetailsDto.setTrackerBean(shippingService.getTrackerByTrackerId(shipmentTable, orderDetailse.getStatus(), paymentDetail));
                orderdetailsDtos.add(orderDetailsDto);
            }
            orderDetailsBean.setOrderDetailsDtos(orderdetailsDtos);
            orderDetailsBean.setStatus("success");
            orderDetailsBean.setStatusCode("200");
            return orderDetailsBean;
        } else {
            orderDetailsBean.setOrderDetailsDtos(orderdetailsDtos);
            orderDetailsBean.setStatus("No order found.");
            orderDetailsBean.setStatusCode("200");
            return orderDetailsBean;
        }
    }

    @Override
    public void cancelOrder(String orderId, Long userId) {
        OrderDetails orderDetails = orderDetailsDao.getOrderDetailsByUserIdAndOrderId(userId, orderId);
        if (orderDetails != null) {
            orderDetails.setReturnStatus(StatusConstants.REQUEST_FOR_RETURN);
            orderDetailsDao.save(orderDetails);
            mailService.returnRequestToAdmin(orderDetails);
        }
    }*/
    @Override
    public String updateProduct(ProductBean productBean) {
        String status = "ERROR: Product can not be update!!";
        Product product = productDao.loadById(productBean.getProductId());
        if (product != null) {
            Category parentCategory = categoryDao.loadById(productBean.getCategoryId());
            product.setName(productBean.getName());
            product.setImgurl(productBean.getImgurl());
            product.setCategoryId(productBean.getCategoryId());
            product.setDescription(productBean.getDescription());
            product.setLastUpdate(new Date());
            product.setVendorId(productBean.getVendorId());     //>>>>>>>>
            product.setParentPath(parentCategory.getParentPath());
            product.setExternalLink(productBean.getExternalLink());
            product.setSpecifications(productBean.getSpecifications());

            product.setShippingTime(productBean.getShippingTime());
            product.setShippingRate(productBean.getShippingRate());

            Product product1 = productDao.save(product);
            /*if (product1 != null) {
                //===========multiple image add==========//
                List<ImageDto> imageDtos = productBean.getImageDtos();
                if (!imageDtos.isEmpty()) {
                    for (ImageDto imageDto : imageDtos) {
                        ProductImg productImg = new ProductImg();
                        productImg.setId(null);
                        productImg.setImgUrl(imageDto.getImgUrl());
                        productImg.setProductId(product1.getId());
                        productImgDao.save(productImg);
                    }
                    status = "Product saved.";
                }
            }*/
        }
        return status;
    }

    @Override
    public ProductBean getProductInventoryById(Long productId) {
        List<SizeColorMapDto> sizeColorMapDtos = new ArrayList<>();
        ProductBean productBean = new ProductBean();
        productBean.setProductId(productId);
        List<ProductSizeColorMap> sizeColorMaps = productSizeColorMapDao.getSizeColorMapByProductId(productId);
        List<Sizee> sizees = sizeeDao.loadAll();
        HashMap<Long, Sizee> mapSize = new HashMap<>();
        for (Sizee sizee : sizees) {
            mapSize.put(sizee.getId(), sizee);
        }
        List<Color> colors = colorDao.loadAll();
        HashMap<Long, Color> mapColor = new HashMap<>();
        for (Color color : colors) {
            mapColor.put(color.getId(), color);
        }
        for (ProductSizeColorMap sizeColorMap : sizeColorMaps) {
            SizeColorMapDto sizeColorMapDto = new SizeColorMapDto();
            if (mapSize.get(sizeColorMap.getSizeId()) != null) {
                sizeColorMapDto.setSizeId(sizeColorMap.getSizeId());
                sizeColorMapDto.setSizeText(mapSize.get(sizeColorMap.getSizeId()).getValu());
            }
            if (mapColor.get(sizeColorMap.getColorId()) != null) {
                sizeColorMapDto.setColorId(sizeColorMap.getColorId());
                sizeColorMapDto.setColorText(mapColor.get(sizeColorMap.getColorId()).getName());
            }
            sizeColorMapDto.setMapId(sizeColorMap.getId());
            sizeColorMapDto.setOrginalPrice(sizeColorMap.getPrice());
            sizeColorMapDto.setOrginalPrice(sizeColorMap.getPrice());
            sizeColorMapDto.setDiscount(sizeColorMap.getDiscount());
            sizeColorMapDto.setSalesPrice(sizeColorMap.getDiscount());
            sizeColorMapDto.setDiscount(this.getPercentage(sizeColorMap.getPrice(), sizeColorMap.getDiscount()));
            sizeColorMapDto.setCount(sizeColorMap.getQuentity());
            sizeColorMapDto.setProductLength(sizeColorMap.getProductLength());
            sizeColorMapDto.setProductHeight(sizeColorMap.getProductHeight());
            sizeColorMapDto.setProductWidth(sizeColorMap.getProductWidth());
            sizeColorMapDto.setProductWeight(sizeColorMap.getProductWeight());
            sizeColorMapDtos.add(sizeColorMapDto);
        }
        productBean.setSizeColorMaps(sizeColorMapDtos);
        List<ImageDto> imageDtos = new ArrayList<>();
        List<ProductImg> productImgs = productImgDao.getProductImgsByProductId(productId);
        for (ProductImg productImg : productImgs) {
            ImageDto imageDto = new ImageDto();
            imageDto.setId(productImg.getId());
            imageDto.setImgUrl(productImg.getImgUrl());
            imageDtos.add(imageDto);
        }
        productBean.setImageDtos(imageDtos);
        return productBean;
    }

    @Override
    public String updateProductInventory(ProductBean productBean) {
        String status = "ERROR: Product Inventory can not be update!!";
        Product product = productDao.loadById(productBean.getProductId());
        if (product != null) {
            //====multiple ProductSizeColorMap added=======//
            List<SizeColorMapDto> sizeColorMapDtos = productBean.getSizeColorMaps();
            if (!sizeColorMapDtos.isEmpty()) {
                for (SizeColorMapDto sizeColorMapDto : sizeColorMapDtos) {
                    ProductSizeColorMap sizeColorMap;
                    if (sizeColorMapDto.getMapId() != null) {
                        sizeColorMap = productSizeColorMapDao.loadById(sizeColorMapDto.getMapId());
                    } else {
                        sizeColorMap = new ProductSizeColorMap();
                        sizeColorMap.setId(null);
                    }
                    sizeColorMap.setColorId(sizeColorMapDto.getColorId());
                    sizeColorMap.setSizeId(sizeColorMapDto.getSizeId());
                    sizeColorMap.setDiscount(sizeColorMapDto.getSalesPrice());
                    sizeColorMap.setPrice(sizeColorMapDto.getOrginalPrice());
                    sizeColorMap.setQuentity(sizeColorMapDto.getCount());
                    sizeColorMap.setProductId(product.getId());
                    sizeColorMap.setProductHeight(sizeColorMapDto.getProductHeight());
                    sizeColorMap.setProductLength(sizeColorMapDto.getProductLength());
                    sizeColorMap.setProductWeight(sizeColorMapDto.getProductWeight());
                    sizeColorMap.setProductWidth(sizeColorMapDto.getProductWidth());
                    ProductSizeColorMap sizeColorMap1 = productSizeColorMapDao.save(sizeColorMap);
                }
            }
            List<ImageDto> imageDtos = productBean.getImageDtos();
            if (imageDtos != null) {
                for (ImageDto imageDto : imageDtos) {
                    ProductImg productImg;
                    if (imageDto.getId() != null) {
                        productImg = productImgDao.loadById(imageDto.getId());
                    } else {
                        productImg = new ProductImg();
                        productImg.setId(null);
                    }
                    productImg.setProductId(product.getId());
                    productImg.setImgUrl(imageDto.getImgUrl());
                    productImgDao.save(productImg);
                }
            }
            status = "Product Inventory update.";
        }
        return status;
    }

    @Override
    public ProductDetailBean getProductSpecifications(Long categoryId) {
        ProductDetailBean detailBean = new ProductDetailBean();
        List<Specification> specifications = specificationDao.getSpecificationsByCategoryId(categoryId);
        HashMap<String, String> specificationMap = new HashMap();
        for (Specification specification : specifications) {
            specificationMap.put(specification.getId().toString(), specification.getFeatures());
        }
        detailBean.setSpecifications(specificationMap);
        return detailBean;
    }

    @Override
    public SpecificationDto getProductSpecificationValue(Long specificationId) {
        Specification specification = specificationDao.loadById(specificationId);
        SpecificationDto specificationDto = new SpecificationDto();
        String[] values = specification.getFeaturesValue().split(",");
        specificationDto.setId(specification.getId());
        specificationDto.setName(specification.getFeatures());
        specificationDto.setValues(Arrays.asList(values));
        return specificationDto;
    }

    @Override
    public List<StatusBean> getTempProducts(String link) {
        boolean status = true;  //success
        String startDate = new Date().toString();
        Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, "(==I==)DATE: " + startDate + "Product link collection start.....\n For the link ( " + link + " )");
        Elements productUrlList = null;
        List<StatusBean> statusBeans = new ArrayList<>();
//        Elements nexturl = null;
        boolean contd = true;
        String productList = link /*"https://www.aliexpress.com/wholesale?minPrice=&maxPrice=&isBigSale=n&isFreeShip=y&isFavorite=all&isMobileExclusive=n&isLocalReturn=n&shipFromCountry=&shipCompanies=&SearchText=jwelry+for+women&CatId=1509&g=y&initiative_id=SB_20170330225112&needQuery=n&isrefine=y"*/;
        Temtproductlinklist temtproductlinklist;
        Temtproductlinklist savedTemtproductlinklist;
        String nexturl = null;
        String firstPart = null;
        String secondPart = null;
        int[] pageNumber = new int[999];
        for (int i = 0; i < 999; i++) {
            pageNumber[i] = (1 + (int) (Math.random() * 1000));
        }
        try {
            Document doc = Jsoup.connect(productList).get();
            productUrlList = doc.select("div.ui-pagination-navi a");
            if (!productUrlList.isEmpty()) {
                nexturl = productUrlList.get(0).attr("abs:href");
                firstPart = nexturl.split(".html")[0];
                firstPart = firstPart.substring(0, firstPart.length() - 1);
                secondPart = nexturl.split(".html")[1];
                secondPart = ".html" + secondPart;
                for (int i = 0; i < 999; i++) {
                    nexturl = firstPart + pageNumber[i] + secondPart;
                    doc = Jsoup.connect(nexturl).get();
                    productUrlList = doc.select(".son-list .list-item .pic a[href]");
                    if (!productUrlList.isEmpty()) {
                        for (Element element : productUrlList) {
                            temtproductlinklist = temtproductlinklistDao.getTemtproductlinklistByLink(element.attr("abs:href"));
                            if (temtproductlinklist == null) {
                                StatusBean statusBean = new StatusBean();
                                temtproductlinklist = new Temtproductlinklist();
                                temtproductlinklist.setLink(element.attr("abs:href"));
                                temtproductlinklist.setStatus(0);
//                    System.out.println("element.toString()" + element.attr("abs:href"));
                                savedTemtproductlinklist = temtproductlinklistDao.save(temtproductlinklist);
                                statusBean.setStatus(String.valueOf(savedTemtproductlinklist.getStatus()));
                                statusBean.setStatusCode(savedTemtproductlinklist.getLink());
                                statusBean.setId(savedTemtproductlinklist.getId());
                                statusBeans.add(statusBean);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            status = false; //failure
            System.out.println("(=============================================)DATE: " + new Date().toString() + "Product link collection get exception.....\n Which started on: " + startDate + "\n" + ex.getLocalizedMessage());
            Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, "(==E==)DATE: " + new Date().toString() + "Product link collection get exception.....\n Which started on: " + startDate + "\n", ex);
            String body = "DATE: " + new Date().toString() + "Product link collection get exception.....\nNext link not found.\n Which started on: " + startDate;
            MailSender.sendEmail("krisanu.nandi@pkweb.in", "Error", body, "subhendu.sett@pkweb.in");
        }
        if (status) {
            System.out.println("=============================================DATE: " + new Date().toString() + "Product link collection end.....\n Which started on: " + startDate);
            Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, "(==I==)DATE: " + new Date().toString() + "Product link collection end.....\n Which started on: " + startDate);
            String body = "DATE: " + new Date().toString() + "Product link collection end.....\n Which started on: " + startDate;
            MailSender.sendEmail("krisanu.nandi@pkweb.in", "Success", body, "subhendu.sett@pkweb.in");
        }
        return statusBeans;
    }

    /*@Override
    public List<StatusBean> getTempProducts(String link) {
        boolean status = true;  //success
        String startDate = new Date().toString();
        Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, "(==I==)DATE: " + startDate + "Product link collection start.....\n For the link ( " + link + " )");
        Elements productUrlList = null;
        List<StatusBean> statusBeans = new ArrayList<>();
        String nexturl = null;
        String firstPart = null;
        String secondPart = null;
        boolean contd = true;
        String productList = link ;
        Temtproductlinklist temtproductlinklist;
        Temtproductlinklist savedTemtproductlinklist;
        int[] pageNumber = new int[999];
        for (int i = 0; i < 999; i++) {
            pageNumber[i] = (1 + (int) (Math.random() * 1000));
        }
        try {
            Document doc = Jsoup.connect(productList).get();
//                productUrlList = doc.select("#hs-below-list-items .list-item .pic a[href]");//for search page
            productUrlList = doc.select(".son-list .list-item .pic a[href]");
            if (productUrlList.isEmpty()) {
                //////////
            } else {
                nexturl = productUrlList.get(0).attr("abs:href");
                firstPart = nexturl.split(".html")[0];
                firstPart = firstPart.substring(0, firstPart.length() - 1);
                secondPart = nexturl.split(".html")[1];
                secondPart = ".html" + secondPart;
                for (int i = 1; i <= 999; i++) {
                    nexturl = firstPart + i + secondPart;
                    doc = Jsoup.connect(nexturl).get();
                    productUrlList = doc.select(".ui-pagination-next");
                    if (!productUrlList.isEmpty()) {
                        temtproductlinklist = temtproductlinklistDao.getTemtproductlinklistByLink(nexturl);
                        if (temtproductlinklist == null) {
                            StatusBean statusBean = new StatusBean();
                            temtproductlinklist = new Temtproductlinklist();
                            temtproductlinklist.setLink(nexturl);
                            temtproductlinklist.setStatus(0);
//                    System.out.println("element.toString()" + element.attr("abs:href"));
                            savedTemtproductlinklist = temtproductlinklistDao.save(temtproductlinklist);
                            statusBean.setStatus(String.valueOf(savedTemtproductlinklist.getStatus()));
                            statusBean.setStatusCode(savedTemtproductlinklist.getLink());
                            statusBean.setId(savedTemtproductlinklist.getId());
                            statusBeans.add(statusBean);
                        }
                    }
                    nexturl = doc.select(".ui-pagination-next");
//                System.out.println("nexturl" + nexturl.toString());
                    if (nexturl.hasClass("page-next")) {
                        productList = nexturl.attr("abs:href");
                        System.out.println("productList == " + productList);
                    } else if (nexturl.hasClass("page-end")) {
                        System.out.println("productList2 ==== ");
                        contd = false;
                    } else {
                        System.out.println("(=============================================)DATE: " + new Date().toString() + "Product link collection get exception.....\nNext link not found.\n Which started on: " + startDate);
                        Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, "(==E==)DATE: " + new Date().toString() + "Product link collection get exception.....\nNext link not found.\n Which started on: " + startDate);
                        contd = false;
                        String body = "DATE: " + new Date().toString() + "Product link collection get exception.....\nNext link not found.\n Which started on: " + startDate;
                        MailSender.sendEmail("krisanu.nandi@pkweb.in", "Error", body, "subhendu.sett@pkweb.in");
                    }
                }
            }
        } catch (Exception ex) {
            status = false; //failure
            System.out.println("(=============================================)DATE: " + new Date().toString() + "Product link collection get exception.....\n Which started on: " + startDate + "\n" + ex.getLocalizedMessage());
            Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, "(==E==)DATE: " + new Date().toString() + "Product link collection get exception.....\n Which started on: " + startDate + "\n", ex);
            String body = "DATE: " + new Date().toString() + "Product link collection get exception.....\nNext link not found.\n Which started on: " + startDate;
            MailSender.sendEmail("krisanu.nandi@pkweb.in", "Error", body, "subhendu.sett@pkweb.in");
        }
        if (status) {
            System.out.println("=============================================DATE: " + new Date().toString() + "Product link collection end.....\n Which started on: " + startDate);
            Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, "(==I==)DATE: " + new Date().toString() + "Product link collection end.....\n Which started on: " + startDate);
            String body = "DATE: " + new Date().toString() + "Product link collection end.....\n Which started on: " + startDate;
            MailSender.sendEmail("krisanu.nandi@pkweb.in", "Success", body, "subhendu.sett@pkweb.in");
        }
        return statusBeans;
    }*/
    @Override
    public List<StatusBean> getTempProductList(int start, int limit) {
//        List<Temtproductlinklist> temtproductlinklists = temtproductlinklistDao.getUnprocessedTempProduct();
        List<Temtproductlinklist> temtproductlinklists = temtproductlinklistDao.getAllTempProduct(start, limit);
        List<StatusBean> statusBeans = new ArrayList<>();
        for (Temtproductlinklist temtproductlinklist : temtproductlinklists) {
            StatusBean statusBean = new StatusBean();
            statusBean.setStatus(String.valueOf(temtproductlinklist.getStatus()));
            statusBean.setStatusCode(temtproductlinklist.getLink());
            statusBean.setId(temtproductlinklist.getId());
            statusBeans.add(statusBean);
        }
        return statusBeans;
    }

    @Override
    public List<StatusBean> loadTempProducts(List<StatusBean> statusBeans) {
        boolean isSuccess = true;
        String startDate = new Date().toString();
        Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, "(==I==)DATE: " + startDate + "Store product details in temp product table start.....");
        try {
//        List<Temtproductlinklist> temtproductlinklists = temtproductlinklistDao.getUnprocessedTempProduct();
            String status = "Link not found";
            for (StatusBean statusBean : statusBeans) {
                status = "Link not found";
                TimeUnit.SECONDS.sleep(60 + (int) (Math.random() * 100));
                Temtproductlinklist temtproductlinklist = temtproductlinklistDao.loadById(statusBean.getId());
                if (temtproductlinklist != null && temtproductlinklist.getStatus() == 0) {
                    TempProduct tempProduct = tempProductDao.getProductByExternelLink(temtproductlinklist.getLink());
                    if (tempProduct == null) {
                        ProductBean productBean = null;
                        String value = "";
                        Elements detailMain;
                        Elements detailSub;
                        Elements specifics;
                        double votes = 0.0;
                        double stars = 0.0;
                        double feedback = 0.0;
                        String url = temtproductlinklist.getLink();
                        try {
                            productBean = new ProductBean();
                            Document doc = Jsoup.connect(url).get();
                            System.out.println("doc.title == " + doc.title());
                            System.out.println("doc.html == " + doc.html());
                            detailMain = doc.select(".rantings-num");
                            if (!detailMain.isEmpty()) {
                                votes = Double.valueOf(detailMain.text().split(" votes")[0].split("\\(")[1]);
                            }
                            detailMain = doc.select(".percent-num");
                            if (!detailMain.isEmpty()) {
                                stars = Double.valueOf(detailMain.text());
                            }
                            detailMain = doc.select("ul.ui-tab-nav li[data-trigger='feedback'] a");
                            if (!detailMain.isEmpty()) {
                                feedback = Double.valueOf(detailMain.text().split("\\(")[1].split("\\)")[0]);
                            }
                            if (votes > 20.0 && stars > 4.8 && feedback > 4.0) {
                                detailMain = doc.select(".detail-wrap .product-name");
                                productBean.setName(detailMain.text());/*.substring(0, Math.min(detailMain.text().length(), 50))*/

                                detailMain = doc.select(".detail-wrap .product-name");
                                productBean.setDescription(detailMain.text());

//                                detailMain = doc.select(".detail-wrap .product-name");
//                                productBean.setDescription(detailMain.text());
                                /**
                                 ********************************
                                 * packaging start **************
                                 */
                                Double weight = 1.0;
                                Double width = 1.0;
                                Double height = 1.0;
                                Double length = 1.0;
                                detailMain = doc.select("div#j-product-desc div.pnl-packaging-main ul li.packaging-item");
                                for (Element element : detailMain) {
                                    String packagingTitle = element.select("span.packaging-title").text();
                                    String packagingDesc = element.select("span.packaging-des").text();
                                    if (packagingTitle.trim().equals("Package Weight:")) {
                                        String str = packagingDesc;
                                        str = str.replaceAll("[^.?0-9]+", " ");
                                        if (Arrays.asList(str.trim().split(" ")) != null) {
                                            if (!Arrays.asList(str.trim().split(" ")).isEmpty()) {
                                                try {
                                                    weight = Double.parseDouble(Arrays.asList(str.trim().split(" ")).get(0));
                                                } catch (Exception e) {
                                                    weight = 1.0;
                                                }
                                            }
                                        }
                                        System.out.println("weight == " + weight);
                                    } else if (packagingTitle.trim().equals("Package Size:")) {
                                        String str = packagingDesc;
                                        str = str.replaceAll("[^.?0-9]+", " ");
                                        if (Arrays.asList(str.trim().split(" ")) != null) {
                                            if (!Arrays.asList(str.trim().split(" ")).isEmpty()) {
                                                try {
                                                    width = Double.parseDouble(Arrays.asList(str.trim().split(" ")).get(0));
                                                    height = Double.parseDouble(Arrays.asList(str.trim().split(" ")).get(1));
                                                    length = Double.parseDouble(Arrays.asList(str.trim().split(" ")).get(2));
                                                } catch (Exception e) {
                                                    width = 1.0;
                                                    height = 1.0;
                                                    length = 1.0;
                                                }
                                            }
                                        }
                                        System.out.println("width == " + width);
                                        System.out.println("height == " + height);
                                        System.out.println("length == " + length);
                                    }
                                }
                                /**
                                 **************************
                                 * packaging end ********
                                 */

                                productBean.setVendorId(1l);//??????????????????????
                                /**
                                 **************************
                                 * Category start ********
                                 */
                                detailMain = doc.select("div.ui-breadcrumb div.container a");
                                String thisCategory = detailMain.last().text().trim();
                                System.out.println("thisCategory == " + thisCategory);
                                Category parentCategory = new Category();
                                for (Element element : detailMain) {
                                    String newCategory;
                                    newCategory = element.text().trim();
                                    System.out.println("newCategory======" + newCategory);
                                    if (newCategory.equals("Home") || newCategory.equals("All Categories") || newCategory.equals("Jewelry & Accessories")) {
                                        if (newCategory.equals("Jewelry & Accessories")) {
                                            parentCategory = categoryDao.getCategoryByName("Jewellery");
                                        }
                                    } else {
                                        Category category = categoryDao.getCategoryByName(newCategory);
                                        if (category != null) {
                                            if (category.getName().equals(thisCategory)) {
                                                productBean.setCategoryId(category.getId());
                                            }
                                            parentCategory = category;
                                        } else {
                                            category = new Category();
                                            category.setId(null);
                                            category.setName(newCategory);
                                            category.setParentId(parentCategory.getId());
                                            category.setParentPath(parentCategory.getParentPath() + parentCategory.getId() + "=");
                                            Category category2 = categoryDao.save(category);
                                            if (category.getName().equals(thisCategory)) {
                                                productBean.setCategoryId(category2.getId());
                                            }
                                            parentCategory = category2;
                                        }
                                    }
                                }
                                /**
                                 **************************
                                 * Category end ********
                                 */

                                productBean.setExternalLink(url);

                                detailMain = doc.select(".product-property-list .property-item");
                                String specifications = "";
                                for (Element element : detailMain) {
                                    specifications = specifications + element.select(".propery-title").get(0).text().replace(",", "/").replace(":", "-") + ":" + element.select(".propery-des").get(0).text().replace(",", "/").replace(":", "-") + ",";//TODO:, check
                                }
                                productBean.setSpecifications(specifications);

                                detailMain = doc.select(".shipping-days[data-role='delivery-days']");
                                System.out.println("value detailMain" + detailMain.toString());
                                value = detailMain.text();
//            productBean.setShippingTime(value);
                                productBean.setShippingTime("45");

                                detailMain = doc.select(".logistics-cost");
                                value = detailMain.text();
                                double discountPrice = 0.0;
                                double actualPrice = 0.0;
                                double markupPrice = 0.0;
                                if (!value.equalsIgnoreCase("Free Shipping")) {
//                f = Double.parseDouble(value.replaceAll(".*?([\\d.]+).*", "$1"));
                                }
                                productBean.setShippingRate(0.0);

                                //====multiple ProductSizeColorMap added=======//
                                //---------------------
                                int flag = 0;
                                specifics = doc.select("#j-product-info-sku dl.p-property-item");
                                List<String> arrPropIds = new ArrayList<>();
                                List<String> arrPropIds2 = new ArrayList<>();
                                HashMap<String, AxpProductDto> AxpProductMap = new HashMap();
                                String id = "";
                                for (Element specific : specifics) {
                                    detailSub = specific.select("dd ul li");
                                    System.out.println("head  ==== " + specific.select("dt").text());
                                    for (Element element : detailSub) {
                                        if (flag > 0) {
                                            id = element.select("a[data-sku-id]").attr("data-sku-id").trim();

                                            Iterator<String> iter = arrPropIds.iterator();
                                            while (iter.hasNext()) {
                                                String arrPropId = iter.next();
                                                iter.remove();
                                                arrPropId = arrPropId + "," + id;
                                                arrPropIds2.add(arrPropId);
                                            }
                                            arrPropIds.addAll(arrPropIds2);
                                            arrPropIds2.removeAll(arrPropIds);
                                            break;
                                        } else {
                                            arrPropIds.add(element.select("a[data-sku-id]").attr("data-sku-id").trim());
                                            if (element.hasClass("item-sku-image")) {
//                            System.out.println("img== " + element.select("a img").attr("title"));
                                            } else {
//                            System.out.println("span== " + element.select("a span").toString());
                                            }
                                        }
                                    }
                                    flag++;
                                }
                                List<AxpProductDto> axpProductDtos = new ArrayList<>();
                                Elements scripts = doc.select("script"); // Get the script part
                                for (Element script : scripts) {
                                    if (script.html().contains("var skuProducts=")) {
                                        String jsonData = "";
                                        jsonData = script.html().split("var skuProducts=")[1].split("var GaData")[0].trim();
                                        jsonData = jsonData.substring(0, jsonData.length() - 1);
                                        //System.out.println("script ======= " + jsonData);
                                        Gson gsonObj = new Gson();
                                        axpProductDtos = Arrays.asList(gsonObj.fromJson(jsonData, AxpProductDto[].class));
                                        for (AxpProductDto axpProductDto : axpProductDtos) {
                                            if (arrPropIds.contains(axpProductDto.getSkuPropIds())) {//if prisent in id list
                                                AxpProductMap.put(axpProductDto.getSkuPropIds(), axpProductDto);
                                            }
                                        }
                                    }
                                }
                                //---------------------
                                List<SizeColorMapDto> sizeColorMapDtos = new ArrayList<>();
                                for (HashMap.Entry<String, AxpProductDto> entry : AxpProductMap.entrySet()) {
                                    String thisId = entry.getKey();
                                    AxpProductDto thisAxpProductDto = entry.getValue();
                                    SkuVal skuVal = thisAxpProductDto.getSkuVal();
                                    if (skuVal.getActSkuCalPrice() != null) {
                                        value = skuVal.getActSkuCalPrice().trim();
                                        discountPrice = CurrencyConverter.usdTOinr(Double.parseDouble(value.replaceAll(".*?([\\d.]+).*", "$1")));
                                        value = skuVal.getSkuCalPrice().trim();
                                        actualPrice = CurrencyConverter.usdTOinr(Double.parseDouble(value.replaceAll(".*?([\\d.]+).*", "$1")));
                                        markupPrice = discountPrice * 0.15 + 100;
                                        discountPrice = Math.ceil((discountPrice + markupPrice) / 10) * 10;;
                                        actualPrice = Math.round(actualPrice + markupPrice);
                                    } else {
                                        discountPrice = 0.0;
                                        value = skuVal.getSkuCalPrice().trim();
                                        actualPrice = CurrencyConverter.usdTOinr(Double.parseDouble(value.replaceAll(".*?([\\d.]+).*", "$1")));
                                        markupPrice = actualPrice * 0.15 + 100;
                                        discountPrice = Math.round(actualPrice + markupPrice);
                                        actualPrice = Math.round(actualPrice + markupPrice);
                                    }

                                    SizeColorMapDto sizeColorMapDto = new SizeColorMapDto();

                                    if (thisAxpProductDto.getSkuAttr().split("#").length > 1) {
                                        Color color = new Color();
                                        color.setName(thisAxpProductDto.getSkuAttr().split("#")[1].split(";")[0]);
                                        color.setCode(" ");
                                        sizeColorMapDto.setColorId(colorDao.save(color).getId()/*null*/);//?????????????????????
                                    } else {
                                        sizeColorMapDto.setColorId(null);//?????????????????????
                                    }
                                    sizeColorMapDto.setSizeId(null);//??????????????????????
//            TimeUnit.SECONDS.sleep(10);
                                    detailMain = doc.select("#j-sku-price");
                                    System.out.println("value2 detailMain" + detailMain.text());
                                    /*value = detailMain.text();/////??????????????????????*/
//                value = "0.25";
                                    sizeColorMapDto.setSalesPrice(discountPrice);
                                    sizeColorMapDto.setOrginalPrice(actualPrice);
                                    sizeColorMapDto.setCount(1l);
                                    sizeColorMapDto.setProductWidth(width);
                                    sizeColorMapDto.setProductLength(length);
                                    sizeColorMapDto.setProductWeight(weight);
                                    sizeColorMapDto.setProductHeight(height);
                                    sizeColorMapDtos.add(sizeColorMapDto);
                                }
                                productBean.setSizeColorMaps(sizeColorMapDtos);

                                //===========multiple image add==========//
                                List<ImageDto> imageDtos = new ArrayList<>();//loop
                                detailMain = doc.select("ul.image-thumb-list span.img-thumb-item img[src]");
                                int flg = 0;
                                for (Element element : detailMain) {
                                    if (flg == 0) {
                                        flg++;
                                        productBean.setImgurl(element.absUrl("src").split(".jpg")[0] + ".jpg");
                                    } else {
                                        ImageDto imageDto = new ImageDto();
                                        imageDto.setImgUrl(element.absUrl("src").split(".jpg")[0] + ".jpg");
                                        imageDtos.add(imageDto);
                                    }
                                }
                                productBean.setImageDtos(imageDtos);
                                if (this.saveTempProduct(productBean).equals("success")) {
                                    temtproductlinklist.setStatus(1);//
                                    temtproductlinklistDao.save(temtproductlinklist);
                                    status = "success";
                                }
                            } else {
                                temtproductlinklist.setStatus(2);//
                                temtproductlinklistDao.save(temtproductlinklist);
                                status = "criteria mismatch";
                            }
                        } catch (Exception ex) {
                            temtproductlinklist.setStatus(4);//
                            temtproductlinklistDao.save(temtproductlinklist);
                            System.out.println("Exception === " + ex);
                            status = "failure";
                            Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, "(==E==)DATE: " + new Date().toString() + "Store product details in temp product table get error in sub process.....\n Link Id: " + statusBean.getId() + "\n Started on" + startDate, ex);
                        }
                    } else {
                        temtproductlinklist.setStatus(3);//
                        temtproductlinklistDao.save(temtproductlinklist);
                        status = "product exsist";
                    }
                }
                statusBean.setStatus(status);
            }
        } catch (Exception e) {
            isSuccess = false;
            Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, "(==E==)DATE: " + new Date().toString() + "Store product details in temp product table get error.....\n Started on" + startDate + "\n", e);
        }
        if (isSuccess) {
            Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, "(==I==)DATE: " + new Date().toString() + "Store product details in temp product table end.....\n Started on" + startDate);
        }
        return statusBeans;
    }

    @Override
    public ProductBeans getAllTempProductsIncloudeZeroAvailable(int start, int limit) {
        List<TempProduct> products = tempProductDao.getAllAvailableProduct(start, limit);   // Find all products 
        List<Long> productIds = new ArrayList<>();
        for (TempProduct product : products) {
            productIds.add(product.getId());
        }
        HashMap<Long, TempProductSizeColorMap> mapSizeColorMaps = tempProductSizeColorMapDao.getSizeColorMapByProductIds(productIds);
        return this.setTempProductBeans(products, mapSizeColorMaps);
    }

    @Override
    public ProductBean getTempProductInventoryById(Long productId) {
        List<SizeColorMapDto> sizeColorMapDtos = new ArrayList<>();
        ProductBean productBean = new ProductBean();
        productBean.setProductId(productId);
        List<TempProductSizeColorMap> sizeColorMaps = tempProductSizeColorMapDao.getSizeColorMapByProductId(productId);
        List<Sizee> sizees = sizeeDao.loadAll();
        HashMap<Long, Sizee> mapSize = new HashMap<>();
        for (Sizee sizee : sizees) {
            mapSize.put(sizee.getId(), sizee);
        }
        List<Color> colors = colorDao.loadAll();
        HashMap<Long, Color> mapColor = new HashMap<>();
        for (Color color : colors) {
            mapColor.put(color.getId(), color);
        }
        for (TempProductSizeColorMap sizeColorMap : sizeColorMaps) {
            SizeColorMapDto sizeColorMapDto = new SizeColorMapDto();
            if (mapSize.get(sizeColorMap.getSizeId()) != null) {
                sizeColorMapDto.setSizeId(sizeColorMap.getSizeId());
                sizeColorMapDto.setSizeText(mapSize.get(sizeColorMap.getSizeId()).getValu());
            }
            if (mapColor.get(sizeColorMap.getColorId()) != null) {
                sizeColorMapDto.setColorId(sizeColorMap.getColorId());
                sizeColorMapDto.setColorText(mapColor.get(sizeColorMap.getColorId()).getName());
            }
            sizeColorMapDto.setMapId(sizeColorMap.getId());
            sizeColorMapDto.setOrginalPrice(sizeColorMap.getPrice());
            sizeColorMapDto.setOrginalPrice(sizeColorMap.getPrice());
            sizeColorMapDto.setDiscount(sizeColorMap.getDiscount());
            sizeColorMapDto.setSalesPrice(sizeColorMap.getDiscount());
            sizeColorMapDto.setDiscount(this.getPercentage(sizeColorMap.getPrice(), sizeColorMap.getDiscount()));
            sizeColorMapDto.setCount(sizeColorMap.getQuantity());
            sizeColorMapDto.setProductLength(sizeColorMap.getProductLength());
            sizeColorMapDto.setProductHeight(sizeColorMap.getProductHeight());
            sizeColorMapDto.setProductWidth(sizeColorMap.getProductWidth());
            sizeColorMapDto.setProductWeight(sizeColorMap.getProductWeight());
            sizeColorMapDtos.add(sizeColorMapDto);
        }
        productBean.setSizeColorMaps(sizeColorMapDtos);
        List<ImageDto> imageDtos = new ArrayList<>();
        List<TempProductImg> productImgs = tempProductImgDao.getProductImgsByProductId(productId);
        for (TempProductImg productImg : productImgs) {
            ImageDto imageDto = new ImageDto();
            imageDto.setId(productImg.getId());
            imageDto.setImgUrl(productImg.getImgUrl());
            imageDtos.add(imageDto);
        }
        productBean.setImageDtos(imageDtos);
        return productBean;
    }

    @Override
    public String updateTempProduct(ProductBean productBean) {
        String status = "ERROR: Product can not be update!!";
        TempProduct product = tempProductDao.loadById(productBean.getProductId());
        if (product != null) {
            Category parentCategory = categoryDao.loadById(productBean.getCategoryId());
            product.setName(productBean.getName());
            product.setImgurl(productBean.getImgurl());
            product.setCategoryId(productBean.getCategoryId());
            product.setDescription(productBean.getDescription());
            product.setLastUpdate(new Date());
            product.setVendorId(productBean.getVendorId());     //>>>>>>>>
            product.setParentPath(parentCategory.getParentPath());
            product.setExternalLink(productBean.getExternalLink());
            product.setSpecifications(productBean.getSpecifications());

            product.setShippingTime(productBean.getShippingTime());
            product.setShippingRate(productBean.getShippingRate());

            TempProduct product1 = tempProductDao.save(product);
            /*if (product1 != null) {
                //===========multiple image add==========//
                List<ImageDto> imageDtos = productBean.getImageDtos();
                if (!imageDtos.isEmpty()) {
                    for (ImageDto imageDto : imageDtos) {
                        ProductImg productImg = new ProductImg();
                        productImg.setId(null);
                        productImg.setImgUrl(imageDto.getImgUrl());
                        productImg.setProductId(product1.getId());
                        productImgDao.save(productImg);
                    }
                    status = "Product saved.";
                }
            }*/
        }
        return status;
    }

    @Override
    public String updateTempProductInventory(ProductBean productBean) {
        String status = "ERROR: Product Inventory can not be update!!";
        TempProduct product = tempProductDao.loadById(productBean.getProductId());
        if (product != null) {
            //====multiple ProductSizeColorMap added=======//
            List<SizeColorMapDto> sizeColorMapDtos = productBean.getSizeColorMaps();
            if (!sizeColorMapDtos.isEmpty()) {
                for (SizeColorMapDto sizeColorMapDto : sizeColorMapDtos) {
                    TempProductSizeColorMap sizeColorMap;
                    if (sizeColorMapDto.getMapId() != null) {
                        sizeColorMap = tempProductSizeColorMapDao.loadById(sizeColorMapDto.getMapId());
                    } else {
                        sizeColorMap = new TempProductSizeColorMap();
                        sizeColorMap.setId(null);
                    }
                    sizeColorMap.setColorId(sizeColorMapDto.getColorId());
                    sizeColorMap.setSizeId(sizeColorMapDto.getSizeId());
                    sizeColorMap.setDiscount(sizeColorMapDto.getSalesPrice());
                    sizeColorMap.setPrice(sizeColorMapDto.getOrginalPrice());
                    sizeColorMap.setQuantity(sizeColorMapDto.getCount());
                    sizeColorMap.setProductId(product.getId());
                    sizeColorMap.setProductHeight(sizeColorMapDto.getProductHeight());
                    sizeColorMap.setProductLength(sizeColorMapDto.getProductLength());
                    sizeColorMap.setProductWeight(sizeColorMapDto.getProductWeight());
                    sizeColorMap.setProductWidth(sizeColorMapDto.getProductWidth());
                    TempProductSizeColorMap sizeColorMap1 = tempProductSizeColorMapDao.save(sizeColorMap);
                }
            }
            List<ImageDto> imageDtos = productBean.getImageDtos();
            if (imageDtos != null) {
                for (ImageDto imageDto : imageDtos) {
                    TempProductImg productImg;
                    if (imageDto.getId() != null) {
                        productImg = tempProductImgDao.loadById(imageDto.getId());
                    } else {
                        productImg = new TempProductImg();
                        productImg.setId(null);
                    }
                    productImg.setProductId(product.getId());
                    productImg.setImgUrl(imageDto.getImgUrl());
                    tempProductImgDao.save(productImg);
                }
            }
            status = "Product Inventory update.";
        }
        return status;
    }

    @Override
    public List<StatusBean> moveTempProductToProduct(List<StatusBean> statusBeans) {
        String status = "failure";
        for (StatusBean statusBean : statusBeans) {
            status = "failure";
            try {
                TempProduct product = tempProductDao.loadById(statusBean.getId());
                List<TempProductSizeColorMap> productSizeColorMaps = tempProductSizeColorMapDao.getSizeColorMapByProductId(statusBean.getId());
                if (product != null && !productSizeColorMaps.isEmpty()) {
                    Product hasProduct = productDao.getProductByExternelLink(product.getExternalLink());
                    String imgUrl;
                    if (hasProduct == null) {
                        List<TempProductImg> productImgs = tempProductImgDao.getProductImgsByProductId(statusBean.getId());
                        ProductBean productBean = new ProductBean();
                        productBean.setCategoryId(product.getCategoryId());
                        productBean.setName(product.getName());
                        imgUrl = "";
                        if (product.getImgurl().contains("cloudinary.com/duqhan")) {
                            imgUrl = product.getImgurl();
                        } else {
                            imgUrl = FileUploader.uploadImage(product.getImgurl()).getUrl();
                        }
                        productBean.setImgurl(imgUrl);
                        productBean.setDescription(product.getDescription());
                        productBean.setVendorId(product.getVendorId());
                        productBean.setExternalLink(product.getExternalLink());
                        productBean.setSpecifications(product.getSpecifications());
                        productBean.setShippingTime(product.getShippingTime());
                        productBean.setShippingRate(product.getShippingRate());
                        //====multiple ProductSizeColorMap added=======//
                        List<SizeColorMapDto> sizeColorMapDtos = new ArrayList();
                        for (TempProductSizeColorMap sizeColorMap : productSizeColorMaps) {
                            SizeColorMapDto sizeColorMapDto = new SizeColorMapDto();
                            sizeColorMapDto.setColorId(sizeColorMap.getColorId());
                            sizeColorMapDto.setSizeId(sizeColorMap.getSizeId());
                            sizeColorMapDto.setSalesPrice(sizeColorMap.getDiscount());
                            sizeColorMapDto.setOrginalPrice(sizeColorMap.getPrice());
                            sizeColorMapDto.setCount(sizeColorMap.getQuantity());
                            sizeColorMapDto.setProductHeight(sizeColorMap.getProductHeight());
                            sizeColorMapDto.setProductLength(sizeColorMap.getProductLength());
                            sizeColorMapDto.setProductWeight(sizeColorMap.getProductWeight());
                            sizeColorMapDto.setProductWidth(sizeColorMap.getProductWidth());
                            sizeColorMapDtos.add(sizeColorMapDto);
                        }
                        productBean.setSizeColorMaps(sizeColorMapDtos);
                        //===========multiple image add==========//
                        List<ImageDto> imageDtos = new ArrayList();
                        imgUrl = "";
                        for (TempProductImg productImg : productImgs) {
                            ImageDto imageDto = new ImageDto();
                            if (productImg.getImgUrl().contains("cloudinary.com/duqhan")) {
                                imgUrl = productImg.getImgUrl();
                            } else {
                                imgUrl = FileUploader.uploadImage(productImg.getImgUrl()).getUrl();
                            }
                            imageDto.setImgUrl(imgUrl);
                            imageDtos.add(imageDto);
                        }
                        productBean.setImageDtos(imageDtos);
                        status = this.saveProduct(productBean);
                        if (status.equals("Product saved.")) {
                            status = "success";
                            for (TempProductSizeColorMap sizeColorMap : productSizeColorMaps) {
                                tempProductSizeColorMapDao.delete(sizeColorMap);
                            }
                            for (TempProductImg productImg : productImgs) {
                                tempProductImgDao.delete(productImg);
                            }
                            tempProductDao.delete(product);
                        }

                    } else {
                        status = "product exists";
                    }
                }
            } catch (Exception e) {
                System.out.println("eeeeeeeeeeee=========== " + e.getLocalizedMessage());
                status = "failure";
            }
            statusBean.setStatus(status);
        }
        return statusBeans;
    }

    public static void main(String[] args) {

//        try {
        //        double discountPrice = 50.045663699;
//        double actualPrice = 100.04563978;
//        double markupPrice = 0.0;
//        markupPrice = discountPrice * 0.15 + 100;
//        discountPrice = Math.ceil((discountPrice + markupPrice) / 10) * 10;;
//        actualPrice = Math.round(actualPrice + markupPrice);
//        System.out.println("discountPrice = " + discountPrice);
//        System.out.println("actualPrice = " + actualPrice);
//        System.out.println("markupPrice = " + markupPrice);
//        System.out.println("dddddd = " + Math.ceil(57.50 / 10) * 10);

        /*Document doc = Jsoup.connect("https://www.aliexpress.com/item/New-Arrivals-15-cols-cheaper-PU-Leather-snap-button-bracelet-fit-18mm-button-armband-jewelry-snap/32731049072.html?spm=2114.01010108.3.9.ozcX84&ws_ab_test=searchweb0_0,searchweb201602_5_10152_10065_10151_10068_5010016_10136_10137_10157_10060_10138_10155_10062_10156_437_10154_10056_10055_10054_10059_303_100031_10099_10103_10102_10096_10147_10052_10053_10107_10050_10142_10051_10084_10083_10119_10080_10082_10081_10110_519_10175_10111_10112_10113_10114_10181_10183_10182_10185_10078_10079_10077_10073_10123_10120_142-10119,searchweb201603_2,ppcSwitch_7&btsid=d4786785-2aa3-4560-9011-107b40d72476&algo_expid=c7433b8e-bef7-42b1-8e99-0ea3cfc2e781-1&algo_pvid=c7433b8e-bef7-42b1-8e99-0ea3cfc2e781").get();

            Elements detailMain;
//            TimeUnit.SECONDS.sleep(20);
            detailMain = doc.select(".ui-box.product-description-main p span");
//            TimeUnit.SECONDS.sleep(30);*/
        boolean status = true;  //success
        String startDate = new Date().toString();
        Elements productUrlList = null;
        List<StatusBean> statusBeans = new ArrayList<>();
//        Elements nexturl = null;
        boolean contd = true;
        String productList = "https://www.aliexpress.com/category/200000102/cuff-bracelets.html?spm=2114.20010608.3.35.EJMIGB&site=glo&tc=af&g=y";
        Temtproductlinklist temtproductlinklist;
        Temtproductlinklist savedTemtproductlinklist;
        String nexturl = null;
        String firstPart = null;
        String secondPart = null;
        int[] pageNumber = new int[999];
        for (int i = 0; i < 999; i++) {
            pageNumber[i] = (1 + (int) (Math.random() * 1000));
        }
        try {
            Document doc = Jsoup.connect(productList).get();
            productUrlList = doc.select("div.ui-pagination-navi a");
            if (!productUrlList.isEmpty()) {
                nexturl = productUrlList.get(0).attr("abs:href");
                firstPart = nexturl.split(".html")[0];
                firstPart = firstPart.substring(0, firstPart.length() - 1);
                secondPart = nexturl.split(".html")[1];
                secondPart = ".html" + secondPart;
                for (int i = 0; i < 999; i++) {
                    nexturl = firstPart + pageNumber[i] + secondPart;
                    System.out.println("nexturl = " + nexturl);
                    doc = Jsoup.connect(nexturl).get();
                    productUrlList = doc.select(".son-list .list-item .pic a[href]");
                    if (!productUrlList.isEmpty()) {
                        for (Element element : productUrlList) {
                            System.out.println("element.attr(\"abs:href\")==" + element.attr("abs:href"));
                        }
                    }
                }
            } else {
                System.out.println("ggggggggggg");
            }

        } catch (Exception ex) {
            System.out.println("ex====" + ex.getLocalizedMessage());
        }
    }
}


/*Unit Type:======piece
Package Weight:======0.1kg (0.22lb.)
Package Size:======10cm x 10cm x 10cm (3.94in x 3.94in x 3.94in)*/
