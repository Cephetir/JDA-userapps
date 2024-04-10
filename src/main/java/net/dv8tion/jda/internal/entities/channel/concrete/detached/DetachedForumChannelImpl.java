/*
 * Copyright 2015 Austin Keener, Michael Ritter, Florian Spieß, and the JDA contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.dv8tion.jda.internal.entities.channel.concrete.detached;

import gnu.trove.map.TLongObjectMap;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.entities.channel.ChannelFlag;
import net.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import net.dv8tion.jda.api.entities.channel.forums.ForumTag;
import net.dv8tion.jda.api.entities.channel.unions.GuildChannelUnion;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.emoji.EmojiUnion;
import net.dv8tion.jda.api.managers.channel.concrete.ForumChannelManager;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import net.dv8tion.jda.api.utils.data.DataObject;
import net.dv8tion.jda.internal.entities.channel.middleman.AbstractGuildChannelImpl;
import net.dv8tion.jda.internal.entities.channel.mixin.attribute.*;
import net.dv8tion.jda.internal.entities.channel.mixin.middleman.StandardGuildChannelMixin;
import net.dv8tion.jda.internal.entities.detached.DetachedGuildImpl;
import net.dv8tion.jda.internal.entities.emoji.CustomEmojiImpl;
import net.dv8tion.jda.internal.interactions.ChannelInteractionPermissions;
import net.dv8tion.jda.internal.utils.cache.SortedSnowflakeCacheViewImpl;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class DetachedForumChannelImpl extends AbstractGuildChannelImpl<DetachedForumChannelImpl>
    implements
        ForumChannel,
        GuildChannelUnion,
        StandardGuildChannelMixin<DetachedForumChannelImpl>,
        IAgeRestrictedChannelMixin<DetachedForumChannelImpl>,
        ISlowmodeChannelMixin<DetachedForumChannelImpl>,
        IWebhookContainerMixin<DetachedForumChannelImpl>,
        IPostContainerMixin<DetachedForumChannelImpl>,
        ITopicChannelMixin<DetachedForumChannelImpl>,
        IInteractionPermissionMixin<DetachedForumChannelImpl>
{
    private ChannelInteractionPermissions interactionPermissions;
    private final SortedSnowflakeCacheViewImpl<ForumTag> tagCache = new SortedSnowflakeCacheViewImpl<>(ForumTag.class, ForumTag::getName, Comparator.naturalOrder());

    private Emoji defaultReaction;
    private String topic;
    private long parentCategoryId;
    private boolean nsfw = false;
    private int position;
    private int flags;
    private int slowmode;
    private int defaultSortOrder;
    private int defaultLayout;
    protected int defaultThreadSlowmode;

    public DetachedForumChannelImpl(long id, DetachedGuildImpl guild)
    {
        super(id, guild);
    }

    @Override
    public boolean isDetached()
    {
        return true;
    }

    @Nonnull
    @Override
    public ForumChannelManager getManager()
    {
        throw detachedException();
    }

    @Nonnull
    @Override
    public List<Member> getMembers()
    {
        throw detachedException();
    }

    @Nonnull
    @Override
    public ChannelAction<ForumChannel> createCopy(@Nonnull Guild guild)
    {
        //TODO share common code with ForumChannelMixin
        throw detachedException();
    }

    @Nonnull
    @Override
    public EnumSet<ChannelFlag> getFlags()
    {
        return ChannelFlag.fromRaw(flags);
    }

    @Nonnull
    @Override
    public SortedSnowflakeCacheViewImpl<ForumTag> getAvailableTagCache()
    {
        return tagCache;
    }

    @Override
    public TLongObjectMap<PermissionOverride> getPermissionOverrideMap()
    {
        throw detachedException();
    }

    @Nonnull
    @Override
    public ChannelInteractionPermissions getInteractionPermissions()
    {
        return interactionPermissions;
    }

    @Override
    public boolean isNSFW()
    {
        return nsfw;
    }

    @Override
    public int getPositionRaw()
    {
        return position;
    }

    @Override
    public long getParentCategoryIdLong()
    {
        return parentCategoryId;
    }

    @Override
    public int getSlowmode()
    {
        return slowmode;
    }

    @Override
    public String getTopic()
    {
        return topic;
    }

    @Override
    public EmojiUnion getDefaultReaction()
    {
        return (EmojiUnion) defaultReaction;
    }

    @Override
    public int getDefaultThreadSlowmode()
    {
        return defaultThreadSlowmode;
    }

    @Nonnull
    @Override
    public SortOrder getDefaultSortOrder()
    {
        return SortOrder.fromKey(defaultSortOrder);
    }

    @Nonnull
    @Override
    public Layout getDefaultLayout()
    {
        return Layout.fromKey(defaultLayout);
    }

    public int getRawFlags()
    {
        return flags;
    }

    public int getRawSortOrder()
    {
        return defaultSortOrder;
    }

    public int getRawLayout()
    {
        return defaultLayout;
    }

    // Setters

    @Override
    public DetachedForumChannelImpl setParentCategory(long parentCategoryId)
    {
        this.parentCategoryId = parentCategoryId;
        return this;
    }

    @Override
    public DetachedForumChannelImpl setPosition(int position)
    {
        this.position = position;
        return this;
    }

    @Override
    public DetachedForumChannelImpl setDefaultThreadSlowmode(int defaultThreadSlowmode)
    {
        this.defaultThreadSlowmode = defaultThreadSlowmode;
        return this;
    }

    public DetachedForumChannelImpl setNSFW(boolean nsfw)
    {
        this.nsfw = nsfw;
        return this;
    }

    public DetachedForumChannelImpl setSlowmode(int slowmode)
    {
        this.slowmode = slowmode;
        return this;
    }

    public DetachedForumChannelImpl setTopic(String topic)
    {
        this.topic = topic;
        return this;
    }

    @Override
    public DetachedForumChannelImpl setFlags(int flags)
    {
        this.flags = flags;
        return this;
    }

    @Override
    public DetachedForumChannelImpl setDefaultReaction(DataObject emoji)
    {
        if (emoji != null && !emoji.isNull("emoji_id"))
            this.defaultReaction = new CustomEmojiImpl("", emoji.getUnsignedLong("emoji_id"), false);
        else if (emoji != null && !emoji.isNull("emoji_name"))
            this.defaultReaction = Emoji.fromUnicode(emoji.getString("emoji_name"));
        else
            this.defaultReaction = null;
        return this;
    }

    @Override
    public DetachedForumChannelImpl setDefaultSortOrder(int defaultSortOrder)
    {
        this.defaultSortOrder = defaultSortOrder;
        return this;
    }

    public DetachedForumChannelImpl setDefaultLayout(int layout)
    {
        this.defaultLayout = layout;
        return this;
    }

    @Nonnull
    @Override
    public DetachedForumChannelImpl setInteractionPermissions(@Nonnull ChannelInteractionPermissions interactionPermissions)
    {
        this.interactionPermissions = interactionPermissions;
        return this;
    }
}
