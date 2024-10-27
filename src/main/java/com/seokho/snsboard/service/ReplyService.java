package com.seokho.snsboard.service;

import com.seokho.snsboard.exception.post.PostNotFoundException;
import com.seokho.snsboard.exception.reply.ReplyNotFoundException;
import com.seokho.snsboard.exception.user.UserNotAllowedException;
import com.seokho.snsboard.model.entity.ReplyEntity;
import com.seokho.snsboard.model.entity.UserEntity;
import com.seokho.snsboard.model.post.*;
import com.seokho.snsboard.model.reply.Reply;
import com.seokho.snsboard.model.reply.ReplyPatchRequestBody;
import com.seokho.snsboard.model.reply.ReplyPostRequestBody;
import com.seokho.snsboard.repository.PostEntityRepository;
import com.seokho.snsboard.repository.ReplyEntityRepository;
import com.seokho.snsboard.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReplyService {

    @Autowired private ReplyEntityRepository replyEntityRepository;

    @Autowired private  PostEntityRepository postEntityRepository;
    @Autowired private UserEntityRepository userEntityRepository;

    private static final List<Post> posts = new ArrayList<>();



    public List<Reply> getRepliesByPostId(Long postId){
        var postEntity = postEntityRepository.findById(postId)
                .orElseThrow(()->new PostNotFoundException(postId));

        var replyEntities =  replyEntityRepository.findByPost(postEntity);
        return replyEntities.stream().map(Reply::from).toList();
    }

    @Transactional
    public Reply createReply(
            Long postId, ReplyPostRequestBody replyPostRequestBody, UserEntity currentUser) {
        var postEntity = postEntityRepository.findById(postId)
                .orElseThrow(()->new PostNotFoundException(postId));

        var replyEntity = replyEntityRepository.save( ReplyEntity.of(replyPostRequestBody.body(),currentUser,postEntity));

        postEntity.setRepliesCount(postEntity.getRepliesCount() + 1);

        return Reply.from(replyEntity);

    }


    public Reply updateReply(
            Long postId,
            Long replyId, ReplyPatchRequestBody replyPatchRequestBody, UserEntity currentUser) {

        postEntityRepository.findById(postId)
                .orElseThrow(()->new PostNotFoundException(postId));
        var replyEntity = replyEntityRepository.findById(replyId)
                .orElseThrow(() -> new ReplyNotFoundException(replyId));

        if (!replyEntity.getUser().equals(currentUser)){
            throw new UserNotAllowedException();
        }

        replyEntity.setBody(replyPatchRequestBody.body());
        return Reply.from(replyEntityRepository.save(replyEntity));

    }

    @Transactional
    public void deleteReply(Long postId, Long replyId, UserEntity currentUser) {
        var postEntity = postEntityRepository.findById(postId)
                .orElseThrow(()->new PostNotFoundException(postId));
        var replyEntity = replyEntityRepository.findById(replyId)
                .orElseThrow(() -> new ReplyNotFoundException(replyId));

        if (!replyEntity.getUser().equals(currentUser)){
            throw new UserNotAllowedException();
        }
        replyEntityRepository.delete(replyEntity);

        postEntity.setRepliesCount(Math.max(0, postEntity.getRepliesCount() - 1));
        postEntityRepository.save(postEntity);

    }



}
