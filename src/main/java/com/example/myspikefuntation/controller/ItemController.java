package com.example.myspikefuntation.controller;

import com.example.myspikefuntation.controller.viewObject.BaseController;
import com.example.myspikefuntation.controller.viewObject.ItemVO;
import com.example.myspikefuntation.error.BusinessException;
import com.example.myspikefuntation.response.CommonResultType;
import com.example.myspikefuntation.service.ItemService;
import com.example.myspikefuntation.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author wangzhe
 * @version 1.0
 * @ClassName ItemController
 * @create 2021-08-05 17:36
 * @description
 */
@RestController
@RequestMapping("/item")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;

    @PostMapping(value = "/create", consumes = {CONTENT_TYPE_FORMED})
    public CommonResultType createItem(@RequestParam("title") String title,
                                       @RequestParam("price") BigDecimal price,
                                       @RequestParam("stock") Integer stock,
                                       @RequestParam("description") String description,
                                       @RequestParam("imgUrl") String imgUrl) throws BusinessException {

        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setDescription(description);
        itemModel.setImgUrl(imgUrl);
        ItemModel itemModelForReturn = itemService.createItem(itemModel);


        ItemVO itemVO = covertVOFromItemModel(itemModelForReturn);
        return CommonResultType.create(itemVO);
    }

    private ItemVO covertVOFromItemModel(ItemModel itemModel) {
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel, itemVO);
        return itemVO;
    }

    /**
     * 商品详情页浏览
     *
     * @param id 商品id
     * @return com.example.myspikefuntation.response.CommonResultType
     * @Date 21:10 2021/8/5
     **/
    @GetMapping("/get/{id}")
    public CommonResultType getItem(@PathVariable("id") Integer id) {
        ItemModel itemModel = itemService.getItemById(id);
        ItemVO itemVO = this.covertVOFromItemModel(itemModel);
        return CommonResultType.create(itemVO);
    }
}
