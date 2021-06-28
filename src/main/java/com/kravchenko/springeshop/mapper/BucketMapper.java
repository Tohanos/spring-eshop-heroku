package com.kravchenko.springeshop.mapper;

import com.kravchenko.springeshop.domain.Bucket;
import com.kravchenko.springeshop.dto.BucketDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.factory.Mappers;

public interface BucketMapper {
    BucketMapper MAPPER = Mappers.getMapper(BucketMapper.class);

    Bucket toBucket(BucketDTO bucketDTO);

    @InheritInverseConfiguration
    BucketDTO fromBucket(Bucket bucket);

}
