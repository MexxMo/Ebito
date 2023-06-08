package ru.digitalmagicians.ebito.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.digitalmagicians.ebito.dto.Role;
import ru.digitalmagicians.ebito.entity.Ads;
import ru.digitalmagicians.ebito.entity.Comment;
import ru.digitalmagicians.ebito.entity.User;
import ru.digitalmagicians.ebito.exception.PermissionDeniedException;

@Service
public class AccessChecker {

    public boolean checkAccess(Ads ads) {
        EbitoUserDetails userDetails = (EbitoUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails.getUser().getRole() != Role.ADMIN && userDetails.getUser().getId() != ads.getAuthor().getId()) {
            throw new PermissionDeniedException();
        }
        return true;
    }

    public boolean checkAccess(Comment comment) {
        EbitoUserDetails userDetails = (EbitoUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails.getUser().getRole() != Role.ADMIN && userDetails.getUser().getId() != comment.getAuthor().getId()) {
            throw new PermissionDeniedException();
        }
        return true;
    }

    public boolean checkAccess() {
        EbitoUserDetails userDetails = (EbitoUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails.getUser().getRole() != Role.ADMIN) {
            throw new PermissionDeniedException();
        }
        return true;
    }

}
