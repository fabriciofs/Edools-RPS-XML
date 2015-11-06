package model.edools;

/**
 * Created by Vitor on 05/11/2015.
 */
public class Product {

	private int id;
	private String title;
	private String subtitle;
	private String description;
	private String logo;
	private String video_url;
	private String video_title;
	private String video_description;
	private boolean published;
	private boolean hidden;

	public boolean isRestricted() {
		return restricted;
	}

	public void setRestricted(boolean restricted) {
		this.restricted = restricted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getVideo_url() {
		return video_url;
	}

	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}

	public String getVideo_title() {
		return video_title;
	}

	public void setVideo_title(String video_title) {
		this.video_title = video_title;
	}

	public String getVideo_description() {
		return video_description;
	}

	public void setVideo_description(String video_description) {
		this.video_description = video_description;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String[] getAllowed_emails() {
		return allowed_emails;
	}

	public void setAllowed_emails(String[] allowed_emails) {
		this.allowed_emails = allowed_emails;
	}

	public String getExpire_date() {
		return expire_date;
	}

	public void setExpire_date(String expire_date) {
		this.expire_date = expire_date;
	}

	public String getAvailable_time_type() {
		return available_time_type;
	}

	public void setAvailable_time_type(String available_time_type) {
		this.available_time_type = available_time_type;
	}

	public String getAvailable_time_length() {
		return available_time_length;
	}

	public void setAvailable_time_length(String available_time_length) {
		this.available_time_length = available_time_length;
	}

	public String getAvailable_time_unit() {
		return available_time_unit;
	}

	public void setAvailable_time_unit(String available_time_unit) {
		this.available_time_unit = available_time_unit;
	}

	public boolean isCertification() {
		return certification;
	}

	public void setCertification(boolean certification) {
		this.certification = certification;
	}

	public String getCertification_min_progress() {
		return certification_min_progress;
	}

	public void setCertification_min_progress(String certification_min_progress) {
		this.certification_min_progress = certification_min_progress;
	}

	public String getMeta_title() {
		return meta_title;
	}

	public void setMeta_title(String meta_title) {
		this.meta_title = meta_title;
	}

	public String getMeta_description() {
		return meta_description;
	}

	public void setMeta_description(String meta_description) {
		this.meta_description = meta_description;
	}

	public String getMeta_keys() {
		return meta_keys;
	}

	public void setMeta_keys(String meta_keys) {
		this.meta_keys = meta_keys;
	}

	public boolean isClasses_auto_generation() {
		return classes_auto_generation;
	}

	public void setClasses_auto_generation(boolean classes_auto_generation) {
		this.classes_auto_generation = classes_auto_generation;
	}

	public String getMax_attendance_type() {
		return max_attendance_type;
	}

	public void setMax_attendance_type(String max_attendance_type) {
		this.max_attendance_type = max_attendance_type;
	}

	public String getMax_attendance_length() {
		return max_attendance_length;
	}

	public void setMax_attendance_length(String max_attendance_length) {
		this.max_attendance_length = max_attendance_length;
	}

	public int[] getForum_section_ids() {
		return forum_section_ids;
	}

	public void setForum_section_ids(int[] forum_section_ids) {
		this.forum_section_ids = forum_section_ids;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	private boolean restricted;
	private String[] allowed_emails;
	private String expire_date;
	private String available_time_type;
	private String available_time_length;
	private String available_time_unit;
	private boolean certification;
	private String certification_min_progress;
	private String meta_title;
	private String meta_description;
	private String meta_keys;
	private boolean classes_auto_generation;
	private String max_attendance_type;
	private String max_attendance_length;
	private int[] forum_section_ids;
	private String created_at;
	private String updated_at;
}
