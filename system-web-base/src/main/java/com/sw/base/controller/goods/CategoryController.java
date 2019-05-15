package com.sw.base.controller.goods;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.cache.service.impl.RedisServiceImpl;
import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.goods.CategoryServiceImpl;
import com.sw.cache.constants.BaseConstants;
import com.sw.cache.util.DataResponse;
import com.sw.common.constants.dict.StatusDict;
import com.sw.common.entity.Entity;
import com.sw.common.entity.goods.Category;
import com.sw.common.entity.goods.CategoryTree;
import com.sw.common.entity.system.File;
import com.sw.common.util.*;
import com.sw.file.service.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system-web/category")
public class CategoryController extends BaseController<CategoryServiceImpl,Category> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    FileServiceImpl fileService;

    @Autowired
    RedisServiceImpl redisService;

    @Autowired
    CategoryServiceImpl categoryService;

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
              /*  if("0".equals(category.getParentId())){
                    continue;
                }*/
                Map<String, String> _map = new HashMap<>();
                map.put(category.getPkCategoryId(), category.getCategoryName());
                _map.put("label", category.getCategoryName());
                _map.put("value", category.getPkCategoryId());
                _map.put("parentId", category.getParentId());
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

        if(CollectionUtil.isNotEmpty(categories)){
            for(Category category:categories){
                EntityWrapper<File> fileEntityWrapper = new EntityWrapper<>();
                fileEntityWrapper.eq("FILE_TYPE", "SW1802");
                fileEntityWrapper.eq("IS_DELETE", 0);
                fileEntityWrapper.eq("FK_ID", category.getPkCategoryId());
                List<File> sysFiles = fileService.selectList(fileEntityWrapper);
                if(CollectionUtil.isNotEmpty(sysFiles)){
                    category.setFileUrl(sysFiles.get(0).getFileUrl());
                }else{
                    category.setFileUrl(DEFAULT_URL);
                }
            }
        }

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
    @RequestMapping(value = "getCategoryInfo/{id}", method = RequestMethod.GET)
    public DataResponse getCategoryInfo(@PathVariable String id){

        DataResponse dataResponse = super.get(id);
        Category category = (Category) dataResponse.get("obj");

        // 获取同级分类
        if(category == null){
            return DataResponse.fail("没有获取到对应的分类");
        }

        // 获取子分类
        EntityWrapper<Category> childWrapper = new EntityWrapper<>();
        childWrapper.eq("PARENT_ID", category.getParentId());
        childWrapper.eq("IS_USED", "SW1302");
        childWrapper.eq("IS_DELETE", 0);
        childWrapper.orderBy(true, "CATEGORY_NO", false);

        List<Category> brotherCategoryList = categoryService.selectList(childWrapper);

        List<CategoryTree> trees = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(brotherCategoryList)){
            for (Category _category:brotherCategoryList){
                CategoryTree categoryTree = setCategoryTree(_category);
                trees.add(categoryTree);
            }
        }

        dataResponse.put("list", trees);
        dataResponse.put("obj", setCategoryTree(category));

        LOGGER.info("==================结束调用 get================");

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

        File file = getFile(category);
        if(file != null){
            category.setFileUrl(file.getFileUrl());
        }else{
            file = new File();
        }

        // 获取子分类
        EntityWrapper<Category> childWrapper = new EntityWrapper<>();
        childWrapper.eq("PARENT_ID", id);
        childWrapper.eq("IS_DELETE", 0);
        childWrapper.eq("IS_USED", "SW1302");
        childWrapper.orderBy(true, "CATEGORY_NO", false);
        List<Category> childCategoryList = categoryService.selectList(childWrapper);
        childCategoryList.add(category);
        for(Category child:childCategoryList){
            File _file = getFile(child);
            if(_file != null){
                child.setFileUrl(_file.getFileUrl());
            } else {
                child.setFileUrl(DEFAULT_URL);
            }
        }

        if(!"0".equals(id)){
            List<CategoryTree> trees = getCategoryTree(childCategoryList, "0");
            dataResponse.put("tree", trees);
        }

        dataResponse.put("file", file);
        dataResponse.put("obj", category);

        LOGGER.info("==================结束调用 get================");

        return dataResponse;
    }

    private File getFile(Category category) {
        EntityWrapper<File> _wrapper = new EntityWrapper<>();
        _wrapper.eq("FILE_TYPE", "SW1802");
        _wrapper.eq("FK_ID", category.getPkCategoryId());
        _wrapper.eq("IS_DELETE", 0);
        List<File> _sysFiles = fileService.selectList(_wrapper);
        File _file = null;
        if(CollectionUtil.isNotEmpty(_sysFiles)){
            _file = _sysFiles.get(0);
        }
        return _file;
    }

    private CategoryTree setCategoryTree(Category category){
        CategoryTree categoryTree = new CategoryTree();
        categoryTree.setId(category.getPkCategoryId());
        categoryTree.setParentId(category.getParentId());
        categoryTree.setName(category.getCategoryName());
        categoryTree.setCode(category.getCategoryNo());
        categoryTree.setLabel(category.getCategoryName());
        categoryTree.setTitle(category.getCategoryName());
        categoryTree.setLevel(category.getCategoryLevel());
        categoryTree.setIsUsed(StatusDict.codeToMessage(category.getIsUsed()));
        return categoryTree;
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public DataResponse add(@RequestBody Category category) {

        String id = StringUtil.getUUID32();
        category.setPkCategoryId(id);
        super.add(category);


        List<Map<String, String>> fileList = category.getFileList();

        if(CollectionUtil.isNotEmpty(fileList)){
            Map<String, String> map = fileList.get(0);
            String url = MapUtil.getMapValue(map, "url");
            String userId = redisService.get("userId");
            // 存入数据库
            File sysFile = new File();
            sysFile.setFileType("SW1802");
            sysFile.setFkId(id);
            sysFile.setFileUrl(url);
            sysFile.setAddTime(DateUtil.getCurrentDateTime());
            sysFile.setIsDelete(0);
            sysFile.setAddUser(userId);
            sysFile.setUpdateTime(DateUtil.getCurrentDateTime());
            sysFile.setUpdateUser(userId);
            fileService.insert(sysFile);
        }
        return DataResponse.success();
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public DataResponse update(@RequestBody Category category) {
        String userId = redisService.get("userId");
        List<Map<String, String>> fileList = category.getFileList();

        if(CollectionUtil.isNotEmpty(fileList)){
            Map<String, String> map = fileList.get(0);
            String url = MapUtil.getMapValue(map, "url");

            EntityWrapper<File> wrapper = new EntityWrapper<>();
            wrapper.eq("FK_ID", category.getPkCategoryId());
            wrapper.eq("IS_DELETE", 0);
            wrapper.eq("FILE_TYPE", "SW1802");

            // 存入数据库
            File sysFile = fileService.selectOne(wrapper);
            if(sysFile != null){
                sysFile.setFileUrl(url);
                sysFile.setUpdateTime(DateUtil.getCurrentDateTime());
                sysFile.setUpdateUser(userId);
                fileService.update(sysFile, wrapper);
            }else{
                sysFile = new File();
                sysFile.setFileType("SW1802");
                sysFile.setFkId(category.getPkCategoryId());
                sysFile.setFileUrl(url);
                sysFile.setAddTime(DateUtil.getCurrentDateTime());
                sysFile.setIsDelete(0);
                sysFile.setAddUser(userId);
                sysFile.setUpdateTime(DateUtil.getCurrentDateTime());
                sysFile.setUpdateUser(userId);
                fileService.insert(sysFile);
            }

        }
        return super.update(category);
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
        if(CollectionUtil.isNotEmpty(list)){
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
                tree.setUrl(obj.getFileUrl());
                trees.add(tree);
            }
        }
        return TreeUtil.build(trees, rootId);
    }

}