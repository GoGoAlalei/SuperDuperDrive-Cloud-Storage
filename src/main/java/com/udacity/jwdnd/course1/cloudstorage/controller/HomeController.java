package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private CredentialService credentialService;

    @PostMapping("/logout")
    public String logout(){
        return "redirect:/login?logout";
    }
    @GetMapping("/logout")
    public String logoutView() {
        return "redirect:/login?logout";
    }

    @GetMapping("/home")
    public String getHomePage(Authentication auth, Model model) {
        Integer uid = userService.getUserId(auth.getName());
        List<File> files = fileService.UserFiles(uid);
        List<Note> notes = noteService.NoteFiles(uid);
        List<Credential> credentials = credentialService.CredentialFiles(uid);
        model.addAttribute("files",files);
        model.addAttribute("notes",notes);
        model.addAttribute("credentials",credentials);
        return "home";
    }

    @PostMapping("/file/upload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileupload, Authentication auth, Model model) throws IOException {

        String errorBanner = null;

        InputStream fis = fileupload.getInputStream();
        //byte[] buffer = new byte[fis.available()];
        //fis.read(buffer);
        FastByteArrayOutputStream output = new FastByteArrayOutputStream();
        fis.transferTo(output);

        if(output.size() == 0) {
            errorBanner = "Invalid empty file!";
            model.addAttribute("ErrorBanner", errorBanner);
            return "result";
        }

        Integer uid = userService.getUserId(auth.getName());
        if(fileService.isDupilicateFileName(uid, fileupload.getOriginalFilename())) {
            errorBanner = "Filename already exists!";
            model.addAttribute("ErrorBanner", errorBanner);
            return "result";
        }

        String upsize = String.valueOf(output.size());
        File upfile = new File(null, fileupload.getOriginalFilename(), fileupload.getContentType(), upsize, uid, output.toByteArray());
        int addrow = fileService.addFile(upfile);

        if(addrow != 1) {
            model.addAttribute("ChangeError", true);
            return "result";
        }

        model.addAttribute("ChangeSuccess", true);
        return "result";

    }

    @GetMapping("/file/view/{fileID}")
    public ResponseEntity<Resource> viewFile(@PathVariable Integer fileID) {
        File vfile = fileService.GetFile(fileID);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(vfile.getContenttype())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + vfile.getFilename() + "\"").body(new ByteArrayResource(vfile.getFiledata()));
    }

    @GetMapping("/file/delete/{fileID}")
    public String deleteFile(@PathVariable Integer fileID, Model model) {
        int delrow = fileService.DeleteFile(fileID);
        if(delrow != 1) {
            model.addAttribute("ChangeError", true);
            return "result";
        }
        else{
            model.addAttribute("ChangeSuccess", true);
            return "result";
        }
    }

    @PostMapping("/note/edit")
    public String addoreditNote(@ModelAttribute Note note, Authentication auth, Model model) {
        System.out.println("uid: " + note.getNoteid());
        if(note.getNoteid() == null) {
            int addrow = noteService.insertNote(new Note(null, note.getNotetitle(), note.getNotedescription(), userService.getUserId(auth.getName())));
            System.out.println("add: " + noteService.nId(note.getNotetitle()));
            if (addrow != 1) {
                model.addAttribute("ChangeError", true);
                return "result";
            }
            else {
                model.addAttribute("ChangeSuccess", true);
                return "result";
            }
        }
        else {
            int updaterow = noteService.updateNote(note.getNoteid(), note.getNotetitle(), note.getNotedescription());
            System.out.println("edit: " + noteService.nId(note.getNotetitle()));
            if (updaterow != 1) {
                model.addAttribute("ChangeError", true);
                return "result";
            }
            else {
                model.addAttribute("ChangeSuccess", true);
                return "result";
            }
        }
    }

    @GetMapping("/note/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, Model model) {
        int delrow = noteService.deleteNote(noteId);
        if(delrow != 1) {
            model.addAttribute("ChangeError", true);
            return "result";
        }
        else{
            model.addAttribute("ChangeSuccess", true);
            return "result";
        }
    }


}
