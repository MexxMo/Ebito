package ru.digitalmagicians.ebito.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.digitalmagicians.ebito.dto.Role;
import ru.digitalmagicians.ebito.entity.Ads;
import ru.digitalmagicians.ebito.entity.Comment;
import ru.digitalmagicians.ebito.exception.PermissionDeniedException;

@Service
public class AccessChecker {

    private boolean hasAccess(Integer creatorId) {
        EbitoUserDetails userDetails = (EbitoUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser().getRole() == Role.ADMIN || userDetails.getUser().getId() == creatorId;
    }

    public boolean checkAccess(Comment comment) {
        if (!hasAccess(comment.getAuthor().getId())) {
            throw new PermissionDeniedException();
        }
        return true;
    }

    public boolean checkAccess(Ads ads) {
        if (!hasAccess(ads.getAuthor().getId())) {
            throw new PermissionDeniedException();
        }
        return true;
    }

}
