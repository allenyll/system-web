package com.sw.base.controller.goods;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.base.controller.BaseController;
import com.sw.base.service.impl.goods.CategoryServiceImpl;
import com.sw.cache.constants.BaseConstants;
import com.sw.cache.util.DataResponse;
import com.sw.common.constants.dict.StatusDict;
import com.sw.common.entity.goods.Category;
import com.sw.common.entity.goods.CategoryTree;
import com.sw.common.util.StringUtil;
import com.sw.common.util.TreeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system-web/category")
public class CategoryController extends BaseController<CategoryServiceImpl,Category> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);


    @Override
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public DataResponse list() {
        DataResponse dataResponse = super.list();
        List<Category> list = (List<Category>) dataResponse.get("list");
        Map<String, String> map = new HashMap<>();
        List<Map<String, String>> newList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){
            for(Category category:list){
                Map<String, String> _map = new HashMap<>();
                map.put(category.getPkCategoryId(), category.getCategoryName());
                _map.put("label", category.getCategoryName());
                _map.put("value", category.getPkCategoryId());
                newList.add(_map);
            }
        }
        dataResponse.put("map", map);
        dataResponse.put("list", newList);
        return dataResponse;
    }

    @ResponseBody
    @RequestMapping(value = "tree", method = RequestMethod.GET)
    public DataResponse tree(String name){
        LOGGER.info("============= {开始调用方法：tree(} =============");
        Map<String, Object> result = new HashMap<>();
        EntityWrapper<Category> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        if(StringUtil.isNotEmpty(name)){
            wrapper.like("CATEGORY_NAME", name);
        }
        List<Category> categories = service.selectList(wrapper);

        List<CategoryTree> list = getCategoryTree(categories, BaseConstants.MENU_ROOT);

        result.put("list", list);
        LOGGER.info("============= {结束调用方法：tree(} =============");
        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "categoryTree", method = RequestMethod.GET)
    public DataResponse categoryTree(){
        DataResponse dataResponse = super.list();
        List<Category> list = (List<Category>) dataResponse.get("list");
        if(!CollectionUtils.isEmpty(list)){
            for(Category _category:list){
                setParentCategory(_category);
            }
        }

        Category category = new Category();
        category.setPkCategoryId("0");
        category.setIsDelete(0);
        category.setCategoryName("顶级节点");
        category.setCategoryNo("top");
        category.setParentId("top");
        list.add(category);

        List<CategoryTree> trees = getCategoryTree(list, "top");

        dataResponse.put("categoryTree", trees);
        return dataResponse;
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public DataResponse get(@PathVariable String id){

        DataResponse dataResponse = super.get(id);
        Category category = (Category) dataResponse.get("obj");

        if(BaseConstants.MENU_ROOT.equals(id)){
            category = new Category();
            category.setPkCategoryId(id);
            category.setCategoryName("顶级节点");
        }else{
            setParentCategory(category);
        }

        dataResponse.put("obj", category);

        LOGGER.info("==================结束调用 get================");

        return dataResponse;
    }

    private void setParentCategory(Category category) {
        String parentId = category.getParentId();
        if(parentId.equals(BaseConstants.MENU_ROOT)){
            category.setParentCategoryName("顶级节点");
        }else{
            EntityWrapper<Category> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("IS_DELETE", 0);
            entityWrapper.eq("PK_CATEGORY_ID", parentId);
            Category _category = service.selectOne(entityWrapper);
            if(_category != null){
                category.setParentCategoryName(_category.getCategoryName());
            }
        }
    }

    private List<CategoryTree> getCategoryTree(List<Category> list, String rootId) {
        List<CategoryTree> trees = new ArrayList<>();
        CategoryTree tree;
        if(!CollectionUtils.isEmpty(list)){
            for(Category obj:list){
                tree = new CategoryTree();
                tree.setId(obj.getPkCategoryId());
                tree.setParentId(obj.getParentId());
                tree.setName(obj.getCategoryName());
                tree.setCode(obj.getCategoryNo());
                tree.setTitle(obj.getCategoryName());
                tree.setLabel(obj.getCategoryName());
                tree.setLevel(obj.getCategoryLevel());
                tree.setIsUsed(StatusDict.codeToMessage(obj.getIsUsed()));
                trees.add(tree);
            }
        }
        return TreeUtil.build(trees, rootId);
    }

}