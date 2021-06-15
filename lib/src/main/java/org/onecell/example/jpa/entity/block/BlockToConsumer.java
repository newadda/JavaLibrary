package org.onecell.example.jpa.entity.block;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "WTWS_BLOCK_TO_CONSUMER")
public class BlockToConsumer {
    @Embeddable
    public static class BlockToConsumerPK implements Serializable{
        Long block_id;
        Long consumer_id;

        public BlockToConsumerPK() {
        }

        public BlockToConsumerPK(Long block_id, Long consumer_id) {
            this.block_id = block_id;
            this.consumer_id = consumer_id;
        }

        public Long getBlock_id() {
            return block_id;
        }

        public void setBlock_id(Long block_id) {
            this.block_id = block_id;
        }

        public Long getConsumer_id() {
            return consumer_id;
        }

        public void setConsumer_id(Long consumer_id) {
            this.consumer_id = consumer_id;
        }

        @Override
        public int hashCode() {
            return (int)(block_id ^ consumer_id);
        }

        @Override
        public boolean equals(Object obj) {
            return ((obj instanceof BlockToConsumerPK) && block_id == ((BlockToConsumerPK)obj).getBlock_id() && consumer_id == ((BlockToConsumerPK) obj).getConsumer_id());
        }
    }

    @EmbeddedId
    @AttributeOverrides(value = {
            @AttributeOverride(name="block_id", column=@Column(name="BLOCK_ID")),
            @AttributeOverride(name="consumer_id", column=@Column(name="CONSUMER_ID"))
    })

    private BlockToConsumerPK id;

    public BlockToConsumerPK getId() {
        return id;
    }

    public void setId(BlockToConsumerPK id) {
        this.id = id;
    }
}
