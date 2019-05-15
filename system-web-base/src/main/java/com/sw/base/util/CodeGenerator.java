package com.sw.base.util;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.io.IOException;

/**
 * 代码生成工具
 * @Author: yu.leilei
 * @Date: 上午 11:07 2018/2/28 0028
 */
public class CodeGenerator {
    public static void main(String[] args) throws InterruptedException, IOException {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //参数为空，表示工程路径
        File directory = new File("");
        //String path=directory.getCanonicalPath()+"\\src\\main\\java";//工程路径+java文件路径，构成生成地址
        String path="/Users/yuleilei/allenyll/my_prj/system-web/system-web-base/src/main/java";
        gc.setOutputDir(path);
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(true);
        /********************1.文件创建人，会体现在类的文档注释上********************/
        gc.setAuthor("yu.leilei");
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("I%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        /*dsc.setTypeConvert(new MySqlTypeConvert(){
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                return super.processTypeConvert(fieldType);
            }
        });*/

        dsc.setDriverName("com.mysql.jdbc.Driver");
//        dsc.setDriverName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dsc.setUrl("jdbc:mysql://localhost:3306/system_web?useUnicode=true");
        dsc.setUsername("root");
        dsc.setPassword("58024myself");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setTablePrefix(new String[] { "", "t_mdm_" });// 此处可以修改为您的表前缀
        //表名、字段名、是否使用下划线命名（默认 false）
        strategy.setDbColumnUnderline(true);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        /********************2.需要生成的表，可以是多个，自行配置********************/
        // 需要生成的表
        strategy.setInclude(new String[] { "snu_customer"});
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(null);
        //TODO 以下包名还需要今后确定后再改
        pc.setController("com.sw.base.controller.system");
        pc.setMapper("com.sw.base.mapper.system");
        pc.setService("com.sw.base.service.system");
        pc.setServiceImpl("com.sw.base.service.impl.system");
        pc.setEntity("com.sw.base.entity");
        pc.setXml("com.sw.base.mapper.system");
        mpg.setPackageInfo(pc);

        // 执行生成
        mpg.execute();
    }

}