package com.wings.helper;


import com.wings.model.YoutubeThumbnails;
import com.wings.model.YoutubeVideoInformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.LinkedHashMap;

import static com.wings.helper.JSONHelper.getJSONObjectFromJSONObject;
import static com.wings.helper.JSONHelper.getStringValueFromJSONObject;

/**
 * Purpose: Use of Youtube data api v3.
 * Ref. @link https://developers.google.com/youtube/v3/getting-started
 *
 * @author NikunjD
 * Created on June 18, 2019
 * Modified on June 18, 2019
 */
public class YoutubeDataAPIV3Helper {

    public enum VideoThumbnailType {
        DEFAULT("default"),
        MEDIUM("medium"),
        HIGH("high"),
        STANDARD("standard"),
        MAX_RESOLUTION("maxres");

        private String thumbnailType = "";

        VideoThumbnailType(String thumbnailType) {
            this.thumbnailType = thumbnailType;
        }

        public String getThumbnailType() {
            return thumbnailType;
        }
    }


    /**
     * Get youtube video thumbnail
     *
     * @param youtubeVideoId youtube video id
     * @param googleAPIKey   google api key
     * @param thumbnailType  type of thumbnail
     * @return youtube thumbnail link
     */
    public static String getYoutubeVideoThumbnail(final String youtubeVideoId, final String googleAPIKey,
                                                  final VideoThumbnailType thumbnailType) {

        final String[] youtubeVideoThumbnail = {""};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String thumbnailValue = "";
                    if (thumbnailType != null) {
                        thumbnailValue = thumbnailType.getThumbnailType();
                    } else {
                        thumbnailValue = VideoThumbnailType.DEFAULT.getThumbnailType();
                    }

                    URL url = new URL("https://www.googleapis.com/youtube/v3/videos?" +
                            "id=" + youtubeVideoId +
                            "&key=" + googleAPIKey +
                            "&fields=items(snippet(thumbnails(" + thumbnailValue + ")))" +
                            "&part=snippet");
                    final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line).append("\n");
                    }
                    br.close();

                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray array = jsonObject.getJSONArray("items");
                    if (array != null && array.length() > 0) {
                        JSONObject items = array.getJSONObject(0);
                        JSONObject snippet =
                                getJSONObjectFromJSONObject(items, "snippet");
                        if (snippet != null) {
                            JSONObject thumbnails =
                                    getJSONObjectFromJSONObject(snippet, "thumbnails");
                            if (thumbnails != null) {
                                JSONObject type =
                                        getJSONObjectFromJSONObject(thumbnails, thumbnailValue);
                                if (type != null) {
                                    youtubeVideoThumbnail[0] = getStringValueFromJSONObject(type, "url");
                                }
                            }
                        }
                    }

                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return youtubeVideoThumbnail[0];
    }


    /**
     * Get available youtube video thumbnail
     *
     * @param youtubeVideoId youtube video id
     * @param googleAPIKey   google api key
     * @return youtube thumbnail list
     */
    public static LinkedHashMap<String, YoutubeThumbnails> getYoutubeVideoThumbnails(final String youtubeVideoId,
                                                                                     final String googleAPIKey) {
        final LinkedHashMap<String, YoutubeThumbnails> linkedHashMap = new LinkedHashMap<>();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://www.googleapis.com/youtube/v3/videos?" +
                            "id=" + youtubeVideoId +
                            "&key=" + googleAPIKey +
                            "&fields=items(snippet(thumbnails))" +
                            "&part=snippet");
                    final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line).append("\n");
                    }
                    br.close();

                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray array = jsonObject.getJSONArray("items");
                    if (array != null && array.length() > 0) {
                        JSONObject items = array.getJSONObject(0);
                        JSONObject snippet =
                                getJSONObjectFromJSONObject(items, "snippet");
                        if (snippet != null) {
                            JSONObject thumbnails =
                                    getJSONObjectFromJSONObject(snippet, "thumbnails");

                            if (thumbnails != null) {

                                JSONObject defaultThumbnail =
                                        getJSONObjectFromJSONObject(thumbnails, VideoThumbnailType.DEFAULT.getThumbnailType());

                                JSONObject mediumThumbnail =
                                        getJSONObjectFromJSONObject(thumbnails, VideoThumbnailType.MEDIUM.getThumbnailType());

                                JSONObject highThumbnail =
                                        getJSONObjectFromJSONObject(thumbnails, VideoThumbnailType.HIGH.getThumbnailType());

                                JSONObject standardThumbnail =
                                        getJSONObjectFromJSONObject(thumbnails, VideoThumbnailType.STANDARD.getThumbnailType());

                                JSONObject maxResThumbnail =
                                        getJSONObjectFromJSONObject(thumbnails, VideoThumbnailType.MAX_RESOLUTION.getThumbnailType());

                                if (defaultThumbnail != null) {
                                    String thumbURL =
                                            getStringValueFromJSONObject(defaultThumbnail, "url");
                                    String thumbWidth =
                                            getStringValueFromJSONObject(defaultThumbnail, "width");
                                    String thumbHeight =
                                            getStringValueFromJSONObject(defaultThumbnail, "height");

                                    YoutubeThumbnails youtubeThumbnails =
                                            new YoutubeThumbnails(thumbURL, thumbWidth, thumbHeight);
                                    linkedHashMap.put(VideoThumbnailType.DEFAULT.getThumbnailType(), youtubeThumbnails);
                                }

                                if (mediumThumbnail != null) {

                                    String thumbURL =
                                            getStringValueFromJSONObject(mediumThumbnail, "url");
                                    String thumbWidth =
                                            getStringValueFromJSONObject(mediumThumbnail, "width");
                                    String thumbHeight =
                                            getStringValueFromJSONObject(mediumThumbnail, "height");

                                    YoutubeThumbnails youtubeThumbnails =
                                            new YoutubeThumbnails(thumbURL, thumbWidth, thumbHeight);
                                    linkedHashMap.put(VideoThumbnailType.MEDIUM.getThumbnailType(),
                                            youtubeThumbnails);
                                }

                                if (highThumbnail != null) {

                                    String thumbURL =
                                            getStringValueFromJSONObject(highThumbnail, "url");
                                    String thumbWidth =
                                            getStringValueFromJSONObject(highThumbnail, "width");
                                    String thumbHeight =
                                            getStringValueFromJSONObject(highThumbnail, "height");

                                    YoutubeThumbnails youtubeThumbnails =
                                            new YoutubeThumbnails(thumbURL, thumbWidth, thumbHeight);
                                    linkedHashMap.put(VideoThumbnailType.HIGH.getThumbnailType(), youtubeThumbnails);
                                }

                                if (standardThumbnail != null) {

                                    String thumbURL =
                                            getStringValueFromJSONObject(standardThumbnail, "url");
                                    String thumbWidth =
                                            getStringValueFromJSONObject(standardThumbnail, "width");
                                    String thumbHeight =
                                            getStringValueFromJSONObject(standardThumbnail, "height");

                                    YoutubeThumbnails youtubeThumbnails =
                                            new YoutubeThumbnails(thumbURL, thumbWidth, thumbHeight);
                                    linkedHashMap.put(VideoThumbnailType.STANDARD.getThumbnailType(),
                                            youtubeThumbnails);
                                }


                                if (maxResThumbnail != null) {
                                    String thumbURL =
                                            getStringValueFromJSONObject(maxResThumbnail, "url");
                                    String thumbWidth =
                                            getStringValueFromJSONObject(maxResThumbnail, "width");
                                    String thumbHeight =
                                            getStringValueFromJSONObject(maxResThumbnail, "height");
                                    YoutubeThumbnails youtubeThumbnails =
                                            new YoutubeThumbnails(thumbURL, thumbWidth, thumbHeight);
                                    linkedHashMap.put(VideoThumbnailType.MAX_RESOLUTION.getThumbnailType(),
                                            youtubeThumbnails);
                                }

                            }
                        }
                    }

                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return linkedHashMap;
    }


    /**
     * Get youtube video thumbnail resolution (width x height)
     *
     * @param youtubeVideoId youtube video id
     * @param googleAPIKey   google api key
     * @param thumbnailType  type of thumbnail
     * @return youtube thumbnail resolution (width x height)
     */
    public static String getYoutubeVideoThumbnailResolution(final String youtubeVideoId, final String googleAPIKey,
                                                            final VideoThumbnailType thumbnailType) {
        final String[] resolution = {""};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String thumbnailValue = "";
                    if (thumbnailType != null) {
                        thumbnailValue = thumbnailType.getThumbnailType();
                    } else {
                        thumbnailValue = VideoThumbnailType.DEFAULT.getThumbnailType();
                    }

                    URL url = new URL("https://www.googleapis.com/youtube/v3/videos?" +
                            "id=" + youtubeVideoId +
                            "&key=" + googleAPIKey +
                            "&fields=items(snippet(thumbnails(" + thumbnailValue + ")))" +
                            "&part=snippet");
                    final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line).append("\n");
                    }
                    br.close();

                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray array = jsonObject.getJSONArray("items");
                    if (array != null && array.length() > 0) {
                        JSONObject items = array.getJSONObject(0);
                        JSONObject snippet =
                                getJSONObjectFromJSONObject(items, "snippet");
                        if (snippet != null) {
                            JSONObject thumbnails =
                                    getJSONObjectFromJSONObject(snippet, "thumbnails");
                            if (thumbnails != null) {
                                JSONObject type =
                                        getJSONObjectFromJSONObject(thumbnails, thumbnailValue);
                                if (type != null) {
                                    resolution[0] =
                                            getStringValueFromJSONObject(type, "width") + " x " +
                                                    getStringValueFromJSONObject(type, "height");
                                }
                            }
                        }
                    }

                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return resolution[0];
    }


    public static YoutubeVideoInformation getVideoInformation(final String youtubeVideoId, final String googleAPIKey) {

        final YoutubeVideoInformation youtubeVideoInformation = new YoutubeVideoInformation();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL("https://www.googleapis.com/youtube/v3/videos?" +
                            "id=" + youtubeVideoId +
                            "&key=" + googleAPIKey +
                            "&part=snippet,contentDetails,statistics");
                    final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line).append("\n");
                    }
                    br.close();

                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray array = jsonObject.getJSONArray("items");
                    if (array != null && array.length() > 0) {
                        JSONObject items = array.getJSONObject(0);

                        youtubeVideoInformation.setId(getStringValueFromJSONObject(items, "id"));
                        youtubeVideoInformation.setTag(getStringValueFromJSONObject(items, "etag"));

                        JSONObject snippet =
                                getJSONObjectFromJSONObject(items, "snippet");
                        if (snippet != null) {
                            youtubeVideoInformation.setPublishedAt(getStringValueFromJSONObject(snippet, "publishedAt"));
                            youtubeVideoInformation.setChannelId(getStringValueFromJSONObject(snippet, "channelId"));
                            youtubeVideoInformation.setTitle(getStringValueFromJSONObject(snippet, "title"));
                            youtubeVideoInformation.setDescription(getStringValueFromJSONObject(snippet, "description"));
                            youtubeVideoInformation.setChannelTitle(getStringValueFromJSONObject(snippet, "channelTitle"));
                            youtubeVideoInformation.setCategoryId(getStringValueFromJSONObject(snippet, "categoryId"));
                            JSONObject thumbnails =
                                    getJSONObjectFromJSONObject(snippet, "thumbnails");
                            if (thumbnails != null) {
                                JSONObject type =
                                        getJSONObjectFromJSONObject(thumbnails, VideoThumbnailType.HIGH.getThumbnailType());
                                if (type != null) {
                                    youtubeVideoInformation.setThumbnail(getStringValueFromJSONObject(type, "url"));
                                }
                            }
                        }

                        JSONObject contentDesc = getJSONObjectFromJSONObject(items, "contentDetails");
                        if (contentDesc != null) {
                            youtubeVideoInformation.setDuration(getStringValueFromJSONObject(contentDesc, "duration"));
                            youtubeVideoInformation.setDimension(getStringValueFromJSONObject(contentDesc, "dimension"));
                            youtubeVideoInformation.setDefinition(getStringValueFromJSONObject(contentDesc, "definition"));
                            youtubeVideoInformation.setCaption(getStringValueFromJSONObject(contentDesc, "caption"));
                            youtubeVideoInformation.setAspectRatio(getStringValueFromJSONObject(contentDesc, "aspectRatio"));
                        }

                        JSONObject statistics = getJSONObjectFromJSONObject(items, "statistics");
                        if (statistics != null) {
                            youtubeVideoInformation.setViewCount(getStringValueFromJSONObject(statistics, "viewCount"));
                            youtubeVideoInformation.setLikeCount(getStringValueFromJSONObject(statistics, "likeCount"));
                            youtubeVideoInformation.setDislikeCount(getStringValueFromJSONObject(statistics, "dislikeCount"));
                            youtubeVideoInformation.setFavoriteCount(getStringValueFromJSONObject(statistics, "favoriteCount"));
                            youtubeVideoInformation.setCommentCount(getStringValueFromJSONObject(statistics, "commentCount"));
                        }

                    }

                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return youtubeVideoInformation;
    }

}
