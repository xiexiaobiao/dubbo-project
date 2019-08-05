package com.example.common.mpg;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.example.common.exception.MybatisPlusException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Classname MysqlGenerator
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-03 21:13
 * @Version 1.0
 **/
public class MysqlGenerator {

    /**
    * @Description: to read the console input
    * @param
    * @params
    * @return
    * @author xiaobiao
    * @date 2019-08-03 21:14
    */
    public static String scanner(String tip){
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("please input " + tip + ":");
        System.out.println(help.toString());
        if (scanner.hasNext()){
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)){
                return ipt;
            }
        }
        throw new MybatisPlusException("please input correct string:" + tip + "!");
    }

    public static void main(String[] args) {

        AutoGenerator mpg = new AutoGenerator();

        //global config
        GlobalConfig gc = new GlobalConfig();
        //获取当前项目的路径
        String projectPath = System.getProperty("user.dir");
        System.out.println("projectPath: "+projectPath);//for test
        gc.setOutputDir(projectPath + "/common/src/main/java");
        gc.setAuthor("xiaobiao");
        gc.setFileOverride(false);
        gc.setBaseColumnList(true); // XML columList
        gc.setOpen(false);
        /*note the  %s will auto replaced by table attributes*/
        //文件名设置
        gc.setEntityName("%sEntity");
        gc.setMapperName("%sDao");
        gc.setXmlName("%sDao");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        gc.setMapperName("%sMapper");
        mpg.setGlobalConfig(gc);

        //数据源设置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/dubbo_db?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        //包设置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("module-name")); // eg: com.example.common.{module-name}.dao
        pc.setParent("com.example.common");
        pc.setEntity("entity");
        pc.setMapper("dao");
        pc.setService("service");
        pc.setServiceImpl("impl");
        pc.setEntity("mapper");
        mpg.setPackageInfo(pc);

        //other customized config
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do  nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        /*经测试，这里的模板设置要去掉，否则出错，因为现在前后分离项目较少使用模板*/
/*        focList.add(new FileOutConfig("/templates/mapper.xml.ftl"){
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "//common/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });*/
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        // close  xml generation,and will save at the root directory
        mpg.setTemplate(new TemplateConfig().setXml(null));

        //策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("com.baomidou.mybatisplus.samples.generator.common.BaseEntity");
        strategy.setEntityLombokModel(true);
//        strategy.setSuperControllerClass("com.baomidou.mybatisplus.samples.generator.common.BaseController");
        strategy.setInclude(scanner("table-name"));//需要包含的表名，允许正则表达式（与exclude二选一配置）
//        strategy.setSuperEntityColumns(""); //父类的公共字段
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        // if use freemarker, be aware the dependency is required
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
