package fudan.se.lab2.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.support.odps.udf.CodecCheck;
import fudan.se.lab2.controller.request.IdRequest;
import fudan.se.lab2.domain.Author;
import fudan.se.lab2.domain.Contribution;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.repository.AuthorRepository;
import fudan.se.lab2.repository.ContributionRepository;
import fudan.se.lab2.service.ContributionService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hibernate.mapping.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Blob;
import java.util.*;

@RestController
@RequestMapping
public class ContributionController {
    private ContributionService contributionService;
    private AuthorRepository authorRepository;
    private ContributionRepository contributionRepository;
    Logger logger = LoggerFactory.getLogger(MeetingController.class);

    @Autowired
    public ContributionController(ContributionService contributionService, AuthorRepository authorRepository, ContributionRepository contributionRepository) {
        this.contributionService = contributionService;
        this.authorRepository = authorRepository;
        this.contributionRepository = contributionRepository;
    }

    @PostMapping(value = "/contribute")
    @ResponseBody
    public ResponseEntity<?> contribute(@RequestParam("file") MultipartFile file, @RequestParam("title") String title,
                                        @RequestParam("summary") String summary, @RequestParam("username") String username,
                                        @RequestParam("meetingFullname") String meetingFullname,
                                        @RequestParam("topics") String topics
    ) throws Exception {

        logger.info("meetingFullname:" + meetingFullname);
        logger.info("title:" + title);
        logger.info("summary:" + summary);
        logger.info("username: " + username);
        long time = System.currentTimeMillis();
        String filename = time + file.getOriginalFilename();
        //File file2 = new File("E:\\lab\\upload\\");//路径得更改成linus系统下的
        File file2 = new File("lab/upload");//路径得更改成linus系统下的
        if (!file2.exists()) {//创建文件夹
            file2.mkdirs();
        }
        //String Filename = "E:\\lab\\upload\\" + filename;//路径得更改成linus系统下的
        String Filename = "lab/upload" + filename;//路径得更改成linus系统下的
        FileOutputStream out = new FileOutputStream(Filename);
        IOUtils.copy(file.getInputStream(), out);
        out.close();
        // List topics1= Arrays.asList(topics.split(",",-1));
        // List authors1= Arrays.asList(authors.split(",",-1));
        List<String> topics1 = JSONArray.parseArray(topics, String.class);
        Contribution contribution = new Contribution(username, meetingFullname, title, summary, Filename, topics1, topics,file.getOriginalFilename());
        return ResponseEntity.ok(contributionService.submit(contribution));
    }


    @PostMapping(value = "/changeContribution")
    @ResponseBody
    public ResponseEntity<?> changeContribution(@RequestParam("id") Long id, @RequestParam("path") String path, @RequestParam("whetherChangeAttachment") Boolean change,
                                                @RequestParam(value = "file", required = false) MultipartFile file, @RequestParam("title") String title,
                                                @RequestParam("summary") String summary, @RequestParam("username") String username,
                                                @RequestParam("meetingFullname") String meetingFullname,
                                                @RequestParam("topics") String topics,@RequestParam("filename") String filename
    ) throws Exception {
        logger.info("id是：" + id);
        logger.info("附件是否更改：" + change);
        if (change) {
            long time = System.currentTimeMillis();
            filename=file.getOriginalFilename();
            String filename2 = time + file.getOriginalFilename();
          //  File file2 = new File("E:\\lab\\upload\\");//路径得更改成linus系统下的
            File file2 = new File("lab/upload");//路径得更改成linus系统下的
            if (!file2.exists()) {//创建文件夹
                file2.mkdirs();
            }
            //String Filename = "E:\\lab\\upload\\" + filename2;//路径得更改成linus系统下的
            String Filename = "lab/upload" + filename2;//路径得更改成linus系统下的
            FileOutputStream out = new FileOutputStream(Filename);
            IOUtils.copy(file.getInputStream(), out);
            out.close();
            path = Filename;
        }
        List<String> topics1 = JSONArray.parseArray(topics, String.class);
        // List topics1= Arrays.asList(topics.split(",",-1));
        //List authors1= Arrays.asList(authors.split(",",-1));

        return ResponseEntity.ok(contributionService.changeContribute(id, path, title, summary, username, meetingFullname, topics1, topics,filename));

    }

    @PostMapping(value = "/checkAuthor")
    @ResponseBody
    public ResponseEntity<?> checkAuthors( @RequestParam("username") String username,@RequestParam("email") String email){
        try {
            List<Author> list=authorRepository.findAllByEmail(email);
            Author author=list.get(0);
            if(author.getUsername().equals(username)){
                logger.info("匹配成功！");
                return ResponseEntity.ok(true);
            }
            else {
                logger.info("与数据库中的的username不匹配！！");
                return ResponseEntity.ok(false);
            }

        }catch (Exception e){
            logger.info("当前邮箱的作者未存进数据库");
            return ResponseEntity.ok(true);
        }

    }

    //id当前稿件的id
    @PostMapping(value = "/addAuthorInfo")
    @ResponseBody
    public ResponseEntity<?> addAuthorInfo(@RequestParam("id") Long id, @RequestParam("username") String username,
                                           @RequestParam("unit") String unit, @RequestParam("area") String area, @RequestParam("email") String email,
                                           @RequestParam(value = "index", required = false) Long index,
                                           @RequestParam("size") Long size) {

        return ResponseEntity.ok(contributionService.addAuthor(id, username, unit, area, email, index));
    }

    @Transactional
    @PostMapping(value = "/deleteAuthors")
    @ResponseBody
    public void delete(@RequestParam("id") Long id) {
        try {
            authorRepository.deleteAllById(id);
            logger.info("删除成功 ");

        } catch (Exception e) {
            logger.info("error: " + e.getMessage());
        }
    }


    @PostMapping(value="/getFile")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(@RequestBody IdRequest idRequest)throws Exception{
        Long id=idRequest.getId();
        Contribution contribution=contributionRepository.findContributionById(id);
        logger.info("path:  "+contribution.getPath());
        String path=contribution.getPath();
        File file=new File(path);
        FileInputStream in=new FileInputStream(file);
        byte[] bytes=FileCopyUtils.copyToByteArray(in);
        in.close();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }


}
