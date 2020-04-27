package fudan.se.lab2.controller;

import com.alibaba.fastjson.JSONArray;
import fudan.se.lab2.domain.Contribution;
import fudan.se.lab2.repository.AuthorRepository;
import fudan.se.lab2.repository.ContributionRepository;
import fudan.se.lab2.service.ContributionService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
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

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping
public class ContributionController {
    private ContributionService contributionService;
    private AuthorRepository authorRepository;
    private ContributionRepository contributionRepository;
    Logger logger = LoggerFactory.getLogger(MeetingController.class);

    @Autowired
    public ContributionController(ContributionService contributionService,AuthorRepository authorRepository,ContributionRepository contributionRepository){
        this.contributionService=contributionService;
        this.authorRepository=authorRepository;
        this.contributionRepository=contributionRepository;
    }

    @PostMapping(value="/contribute")
    @ResponseBody
    public ResponseEntity<?> contribute(@RequestParam("file") MultipartFile file, @RequestParam("title")String title,
                                        @RequestParam("summary")String summary, @RequestParam("username") String username,
                                        @RequestParam("meetingFullname")String meetingFullname,
                                        @RequestParam("topics") String topics
                                        )throws Exception
    {

        logger.info("meetingFullname:"+meetingFullname);
        logger.info("title:"+title);
        logger.info("summary:"+summary);
        logger.info("username: "+username);
        long time=System.currentTimeMillis();
        String filename=time+file.getOriginalFilename();
        File file2 = new File("E:\\lab\\upload\\");//路径得更改成linus系统下的
        if (!file2.exists()) {//创建文件夹
            file2.mkdirs();
        }
        String Filename="E:\\lab\\upload\\"+filename;//路径得更改成linus系统下的
        FileOutputStream out=new FileOutputStream(Filename);
        IOUtils.copy(file.getInputStream(),out);
        out.close();
       // List topics1= Arrays.asList(topics.split(",",-1));
       // List authors1= Arrays.asList(authors.split(",",-1));
        List<String> topics1= JSONArray.parseArray(topics,String.class);
        Contribution contribution=new Contribution(username,meetingFullname,title,summary,Filename,topics1);
        return ResponseEntity.ok(contributionService.submit(contribution));
    }

    @Transactional
    @PostMapping(value="/changeContribution")
    @ResponseBody
    public ResponseEntity<?> changeContribution(@RequestParam("id")Long id,@RequestParam("path")String path,@RequestParam("whetherChangeAttachment")Boolean change,
                                                @RequestParam("file") MultipartFile file, @RequestParam("title")String title,
                                                @RequestParam("summary")String summary, @RequestParam("username") String username,
                                                @RequestParam("meetingFullname")String meetingFullname,
                                                @RequestParam("topics") String topics
                                               )throws Exception
    {
        logger.info("id是："+id);
        logger.info("附件是否更改："+change);
        if(change){
            long time=System.currentTimeMillis();
            String filename=time+file.getOriginalFilename();
            File file2 = new File("E:\\lab\\upload\\");//路径得更改成linus系统下的
            if (!file2.exists()) {//创建文件夹
                file2.mkdirs();
            }
            String Filename="E:\\lab\\upload\\"+filename;//路径得更改成linus系统下的
            FileOutputStream out=new FileOutputStream(Filename);
            IOUtils.copy(file.getInputStream(),out);
            out.close();
            path=Filename;
        }
        List<String> topics1= JSONArray.parseArray(topics,String.class);
       // List topics1= Arrays.asList(topics.split(",",-1));
        //List authors1= Arrays.asList(authors.split(",",-1));
        try {
            authorRepository.deleteAllById(id);
        }catch (Exception e){
            logger.info("error: "+e.getMessage());
        }

        return ResponseEntity.ok(contributionService.changeContribute(id,path,title,summary,username,meetingFullname,topics1));

    }



    @PostMapping(value="/addAuthorInfo")
    @ResponseBody
    public ResponseEntity<?> addAuthorInfo(@RequestParam("id")Long id,@RequestParam("username")String username,
                                           @RequestParam("unit")String unit,@RequestParam("area")String area,@RequestParam("email")String email){
        return ResponseEntity.ok(contributionService.addAuthor(id,username,unit,area,email));
    }

    @PostMapping(value="/getFile")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(@RequestParam("id")Long id)throws Exception{
        Contribution contribution=contributionRepository.findContributionById(id);
        logger.info("path:  "+contribution.getPath());
        String path=contribution.getPath();
        File file=new File(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);

    }

}
