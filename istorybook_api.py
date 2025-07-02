from fastapi import FastAPI, HTTPException, UploadFile, File, Form
from fastapi.responses import JSONResponse, FileResponse
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from typing import Optional, Dict, Any
import uuid
import os
import json
from datetime import datetime
from PIL import Image, ImageDraw, ImageFont
import io
import base64

app = FastAPI(
    title="iStoryBook API",
    description="MVP 0.0.1 - Minimum Viable Product for Story Book Generation",
    version="0.0.1"
)

# CORS middleware for frontend integration
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Data models
class UserInfo(BaseModel):
    """微信用户基本信息模型"""
    user_id: str
    nickname: str
    avatar_url: Optional[str] = None
    gender: Optional[int] = None  # 1: male, 2: female, 0: unknown
    language: Optional[str] = "zh_CN"
    city: Optional[str] = None
    province: Optional[str] = None
    country: Optional[str] = None

class StoryRequest(BaseModel):
    """故事生成请求模型"""
    user_id: str
    story_template: str = "default"
    background_image: Optional[str] = None

class ShareCardRequest(BaseModel):
    """分享卡片请求模型"""
    user_id: str
    story_id: str
    card_text: Optional[str] = None

# In-memory storage (replace with database in production)
users_db: Dict[str, UserInfo] = {}
stories_db: Dict[str, Dict[str, Any]] = {}
cards_db: Dict[str, Dict[str, Any]] = {}

# Ensure directories exist
os.makedirs("uploads", exist_ok=True)
os.makedirs("generated", exist_ok=True)

@app.get("/")
async def root():
    """API根路径"""
    return {
        "message": "Welcome to iStoryBook API",
        "version": "0.0.1",
        "description": "MVP - 针对天使用户的最小功能组合"
    }

# 1. 设置: 获取微信用户基本信息，图像作为角色默认图像
@app.post("/api/v1/setup/user")
async def setup_user(user_info: UserInfo):
    """
    设置用户基本信息
    获取微信用户基本信息，图像作为角色默认图像
    """
    try:
        # Store user info
        users_db[user_info.user_id] = user_info
        
        return {
            "status": "success",
            "message": "用户信息设置成功",
            "data": {
                "user_id": user_info.user_id,
                "nickname": user_info.nickname,
                "setup_time": datetime.now().isoformat()
            }
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"用户设置失败: {str(e)}")

@app.post("/api/v1/setup/avatar")
async def upload_avatar(
    user_id: str = Form(...),
    avatar: UploadFile = File(...)
):
    """
    上传用户头像作为角色默认图像
    """
    try:
        # Validate file type
        if not avatar.content_type.startswith("image/"):
            raise HTTPException(status_code=400, detail="只支持图片文件")
        
        # Generate unique filename
        file_extension = avatar.filename.split(".")[-1]
        avatar_filename = f"{user_id}_avatar_{uuid.uuid4().hex}.{file_extension}"
        avatar_path = os.path.join("uploads", avatar_filename)
        
        # Save uploaded file
        with open(avatar_path, "wb") as f:
            content = await avatar.read()
            f.write(content)
        
        # Update user info
        if user_id in users_db:
            users_db[user_id].avatar_url = avatar_path
        
        return {
            "status": "success",
            "message": "头像上传成功",
            "data": {
                "user_id": user_id,
                "avatar_url": avatar_path,
                "upload_time": datetime.now().isoformat()
            }
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"头像上传失败: {str(e)}")

# 2. 故事: 一个静态故事背景图，添加微信用户基本信息，生成卡片
@app.post("/api/v1/story/generate")
async def generate_story(request: StoryRequest):
    """
    生成故事卡片
    一个静态故事背景图，添加微信用户基本信息，生成卡片
    """
    try:
        # Validate user exists
        if request.user_id not in users_db:
            raise HTTPException(status_code=404, detail="用户不存在，请先设置用户信息")
        
        user_info = users_db[request.user_id]
        story_id = str(uuid.uuid4())
        
        # Create story card with user info
        story_card = create_story_card(user_info, request.story_template)
        
        # Store story info
        stories_db[story_id] = {
            "story_id": story_id,
            "user_id": request.user_id,
            "template": request.story_template,
            "card_path": story_card["card_path"],
            "created_time": datetime.now().isoformat(),
            "user_info": user_info.dict()
        }
        
        return {
            "status": "success",
            "message": "故事卡片生成成功",
            "data": {
                "story_id": story_id,
                "card_path": story_card["card_path"],
                "preview_url": f"/api/v1/story/preview/{story_id}",
                "created_time": stories_db[story_id]["created_time"]
            }
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"故事生成失败: {str(e)}")

@app.get("/api/v1/story/preview/{story_id}")
async def preview_story(story_id: str):
    """预览故事卡片"""
    if story_id not in stories_db:
        raise HTTPException(status_code=404, detail="故事不存在")
    
    story = stories_db[story_id]
    card_path = story["card_path"]
    
    if not os.path.exists(card_path):
        raise HTTPException(status_code=404, detail="故事卡片文件不存在")
    
    return FileResponse(card_path, media_type="image/png")

# 3. 分享: 合成一张卡片图片可以分享到朋友圈
@app.post("/api/v1/share/card")
async def create_share_card(request: ShareCardRequest):
    """
    生成分享卡片
    合成一张卡片图片可以分享到朋友圈
    """
    try:
        # Validate story exists
        if request.story_id not in stories_db:
            raise HTTPException(status_code=404, detail="故事不存在")
        
        story = stories_db[request.story_id]
        user_info = users_db[request.user_id]
        
        # Create shareable card
        share_card = create_share_card_image(story, user_info, request.card_text)
        
        share_id = str(uuid.uuid4())
        cards_db[share_id] = {
            "share_id": share_id,
            "story_id": request.story_id,
            "user_id": request.user_id,
            "card_path": share_card["card_path"],
            "card_text": request.card_text,
            "created_time": datetime.now().isoformat()
        }
        
        return {
            "status": "success",
            "message": "分享卡片生成成功",
            "data": {
                "share_id": share_id,
                "card_path": share_card["card_path"],
                "share_url": f"/api/v1/share/preview/{share_id}",
                "download_url": f"/api/v1/share/download/{share_id}",
                "created_time": cards_db[share_id]["created_time"]
            }
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"分享卡片生成失败: {str(e)}")

@app.get("/api/v1/share/preview/{share_id}")
async def preview_share_card(share_id: str):
    """预览分享卡片"""
    if share_id not in cards_db:
        raise HTTPException(status_code=404, detail="分享卡片不存在")
    
    card = cards_db[share_id]
    card_path = card["card_path"]
    
    if not os.path.exists(card_path):
        raise HTTPException(status_code=404, detail="分享卡片文件不存在")
    
    return FileResponse(card_path, media_type="image/png")

@app.get("/api/v1/share/download/{share_id}")
async def download_share_card(share_id: str):
    """下载分享卡片"""
    if share_id not in cards_db:
        raise HTTPException(status_code=404, detail="分享卡片不存在")
    
    card = cards_db[share_id]
    card_path = card["card_path"]
    
    if not os.path.exists(card_path):
        raise HTTPException(status_code=404, detail="分享卡片文件不存在")
    
    return FileResponse(
        card_path, 
        media_type="image/png",
        filename=f"istorybook_share_{share_id}.png"
    )

# Utility functions for image generation
def create_story_card(user_info: UserInfo, template: str = "default") -> Dict[str, Any]:
    """创建故事卡片"""
    # Create a simple story card with user info
    width, height = 800, 600
    img = Image.new('RGB', (width, height), color='lightblue')
    draw = ImageDraw.Draw(img)
    
    try:
        # Try to use a font (fallback to default if not available)
        font_large = ImageFont.truetype("arial.ttf", 36)
        font_medium = ImageFont.truetype("arial.ttf", 24)
        font_small = ImageFont.truetype("arial.ttf", 18)
    except:
        font_large = ImageFont.load_default()
        font_medium = ImageFont.load_default()
        font_small = ImageFont.load_default()
    
    # Draw story background
    draw.rectangle([50, 50, width-50, height-50], fill='white', outline='navy', width=3)
    
    # Add title
    title = "我的故事书"
    draw.text((width//2, 100), title, fill='navy', font=font_large, anchor="mm")
    
    # Add user info
    y_pos = 200
    draw.text((100, y_pos), f"主角: {user_info.nickname}", fill='black', font=font_medium)
    
    if user_info.city:
        y_pos += 40
        draw.text((100, y_pos), f"来自: {user_info.city}", fill='black', font=font_small)
    
    # Add story content
    y_pos += 80
    story_text = f"这是 {user_info.nickname} 的专属故事..."
    draw.text((100, y_pos), story_text, fill='darkblue', font=font_medium)
    
    # Add timestamp
    timestamp = datetime.now().strftime("%Y-%m-%d %H:%M")
    draw.text((100, height-100), f"创建时间: {timestamp}", fill='gray', font=font_small)
    
    # Save card
    card_filename = f"story_{user_info.user_id}_{uuid.uuid4().hex}.png"
    card_path = os.path.join("generated", card_filename)
    img.save(card_path)
    
    return {
        "card_path": card_path,
        "width": width,
        "height": height
    }

def create_share_card_image(story: Dict[str, Any], user_info: UserInfo, card_text: Optional[str] = None) -> Dict[str, Any]:
    """创建分享卡片图片"""
    width, height = 600, 800
    img = Image.new('RGB', (width, height), color='#f0f0f0')
    draw = ImageDraw.Draw(img)
    
    try:
        font_large = ImageFont.truetype("arial.ttf", 32)
        font_medium = ImageFont.truetype("arial.ttf", 20)
        font_small = ImageFont.truetype("arial.ttf", 16)
    except:
        font_large = ImageFont.load_default()
        font_medium = ImageFont.load_default()
        font_small = ImageFont.load_default()
    
    # Draw card background
    draw.rectangle([30, 30, width-30, height-30], fill='white', outline='#4a90e2', width=4)
    
    # Add header
    draw.text((width//2, 80), "iStoryBook", fill='#4a90e2', font=font_large, anchor="mm")
    draw.text((width//2, 120), "我的专属故事", fill='gray', font=font_medium, anchor="mm")
    
    # Add user avatar placeholder (if exists)
    if user_info.avatar_url and os.path.exists(user_info.avatar_url):
        try:
            avatar = Image.open(user_info.avatar_url)
            avatar = avatar.resize((100, 100))
            img.paste(avatar, (width//2-50, 150))
        except:
            # Draw placeholder circle
            draw.ellipse([width//2-50, 150, width//2+50, 250], fill='lightgray', outline='gray')
    else:
        # Draw placeholder circle
        draw.ellipse([width//2-50, 150, width//2+50, 250], fill='lightgray', outline='gray')
    
    # Add user name
    draw.text((width//2, 280), user_info.nickname, fill='black', font=font_medium, anchor="mm")
    
    # Add custom text if provided
    if card_text:
        y_pos = 350
        # Simple text wrapping
        words = card_text.split()
        lines = []
        current_line = []
        for word in words:
            if len(' '.join(current_line + [word])) <= 30:  # Rough character limit
                current_line.append(word)
            else:
                if current_line:
                    lines.append(' '.join(current_line))
                current_line = [word]
        if current_line:
            lines.append(' '.join(current_line))
        
        for line in lines[:5]:  # Limit to 5 lines
            draw.text((width//2, y_pos), line, fill='darkblue', font=font_small, anchor="mm")
            y_pos += 25
    
    # Add footer
    draw.text((width//2, height-80), "扫码分享更多故事", fill='gray', font=font_small, anchor="mm")
    draw.text((width//2, height-50), f"Created: {datetime.now().strftime('%Y-%m-%d')}", 
              fill='lightgray', font=font_small, anchor="mm")
    
    # Save share card
    share_filename = f"share_{story['story_id']}_{uuid.uuid4().hex}.png"
    share_path = os.path.join("generated", share_filename)
    img.save(share_path)
    
    return {
        "card_path": share_path,
        "width": width,
        "height": height
    }

# Health check and info endpoints
@app.get("/api/v1/health")
async def health_check():
    """健康检查"""
    return {
        "status": "healthy",
        "timestamp": datetime.now().isoformat(),
        "version": "0.0.1"
    }

@app.get("/api/v1/stats")
async def get_stats():
    """获取系统统计信息"""
    return {
        "users_count": len(users_db),
        "stories_count": len(stories_db),
        "shares_count": len(cards_db),
        "timestamp": datetime.now().isoformat()
    }

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000, debug=True)