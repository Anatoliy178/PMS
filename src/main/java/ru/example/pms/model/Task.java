package ru.example.pms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Уникальный идентификатор задачи. Генерируется автоматически.

    private String title;  // Заголовок задачи.
    private String description;  // Описание задачи.
    private LocalDateTime deadline; // Срок выполнения задачи. Тип — LocalDateTime.
    private String assignedTo; // Имя или ID пользователя, которому назначена задача.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;  // Статус задачи (например, TODO, IN_PROGRESS, DONE).

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;  // Ссылка на проект, к которому относится задача.

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;  // Дата создания задачи. Не обновляется при изменениях (updatable = false).

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // Дата последнего обновления.

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }
}

