package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.ContributionRequest;
import fudan.se.lab2.domain.Contribution;
import fudan.se.lab2.service.ContributionService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@RestController
@RequestMapping
public class ContributionController {
    private ContributionService contributionService;
    Logger logger = LoggerFactory.getLogger(MeetingController.class);

    @Autowired
    public ContributionController(ContributionService contributionService){
        this.contributionService=contributionService;
    }

    @PostMapping(value="/contribute")
    @ResponseBody
    public ResponseEntity<?> contribute(@RequestParam("file") MultipartFile file, @RequestParam("title")String title,
                                        @RequestParam("summary")String summary, @RequestParam("username") String username,
                                        @RequestParam("meetingFullname")String meetingFullname,
                                        @RequestParam("topics") List<String> topics,
                                        @RequestParam("authors")List<String> authors )throws Exception
    {

        logger.info("meetingFullname:"+meetingFullname);
        logger.info("title:"+title);
        logger.info("summary:"+summary);
        logger.info("username: "+username);
        logger.info("topics个数："+topics.size());
        logger.info("authors个数"+authors.size());
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
        Contribution contribution=new Contribution(username,meetingFullname,title,summary,Filename,topics,authors);
        return ResponseEntity.ok(contributionService.submit(contribution));
    }

    @PostMapping(value="/changeContribution")
    @ResponseBody
    public ResponseEntity<?> changeContribution(@RequestParam("id")Long id,@RequestParam("path")String path,@RequestParam("whetherChangeAttachment")Boolean change,
                                                @RequestParam("file") MultipartFile file, @RequestParam("title")String title,
                                                @RequestParam("summary")String summary, @RequestParam("username") String username,
                                                @RequestParam("meetingFullname")String meetingFullname,
                                                @RequestParam("topics") List<String> topics,
                                                @RequestParam("authors")List<String> authors)throws Exception
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
        return ResponseEntity.ok(contributionService.changeContribute(id,path,title,summary,username,meetingFullname,topics,authors));

    }


}
