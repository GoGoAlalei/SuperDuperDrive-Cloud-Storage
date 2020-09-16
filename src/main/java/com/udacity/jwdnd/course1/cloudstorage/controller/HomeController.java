package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
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
    //@Autowired
    //private EncryptionService encryptionService;

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
        //model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    /*
      home.html:
        <td>
            <button type="button" id="editcredential-button" class="btn btn-success"
                    th:onclick="javascript:showCredentialModal([[${credential.credentialid}]],[[${credential.url}]],[[${credential.username}]],[[${encryptionService.decryptValue(credential.password, credential.key)}]])">Edit</button>
            <a class="btn btn-danger" id="deletecredential-button" th:href="@{/credential/delete/{credentialId}(credentialId=${credential.credentialid})}">Delete</a>
        </td>
     */

    @PostMapping("/file/upload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileupload, Authentication auth, Model model) throws IOException {

        String errorBanner = null;

        if(fileupload.isEmpty()) {
            errorBanner = "Please select a valid file to upload!";
            model.addAttribute("ErrorBanner", errorBanner);
            return "result";
        }

        if (fileupload.getSize() > 10485760) {
            errorBanner = "Maximum file size is 10M.";
            model.addAttribute("ErrorBanner", errorBanner);
            return "result";
        }

        Integer uid = userService.getUserId(auth.getName());
        if(fileService.isDupilicateFileName(uid, fileupload.getOriginalFilename())) {
            errorBanner = "Filename already exists!";
            model.addAttribute("ErrorBanner", errorBanner);
            return "result";
        }

        InputStream fis = fileupload.getInputStream();
        //byte[] buffer = new byte[fis.available()];
        //fis.read(buffer);
        FastByteArrayOutputStream output = new FastByteArrayOutputStream();
        fis.transferTo(output);

        String upsize = String.valueOf(output.size());
        File upfile = new File(null, fileupload.getOriginalFilename(), fileupload.getContentType(), upsize, uid, output.toByteArray());
        int addrow = fileService.addFile(upfile);

        if (addrow != 1) {
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
    public String AddEditNote(@ModelAttribute Note note, Authentication auth, Model model) {

        if(note.getNoteid() == null) {
            note.setUserid(userService.getUserId(auth.getName()));
            int addrow = noteService.insertNote(note);
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
            int updaterow = noteService.updateNote(note);
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

    @PostMapping("/credential/edit")
    public String AddEditCredential(@ModelAttribute Credential credential, Authentication auth, Model model) {
        if(credential.getCredentialid() == null) {
            credential.setUserid(userService.getUserId(auth.getName()));
            int addrow = credentialService.insertCredential(credential);
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
            int updaterow = credentialService.updateCredential(credential);
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

    @GetMapping("/credential/delete/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId, Model model) {
        int delrow = credentialService.deleteCredential(credentialId);
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
